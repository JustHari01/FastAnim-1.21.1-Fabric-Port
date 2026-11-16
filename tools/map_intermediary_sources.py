from __future__ import annotations

import argparse
import re
import zipfile
from collections import defaultdict
from pathlib import Path

parser = argparse.ArgumentParser()
parser.add_argument("--input", required=True)
parser.add_argument("--mappings", required=True)
parser.add_argument("--output", required=True)
args = parser.parse_args()

with zipfile.ZipFile(args.mappings) as archive:
    lines = archive.read("mappings/mappings.tiny").decode("utf-8").splitlines()

classes: dict[str, str] = {}
members: dict[str, dict[str, set[str]]] = {
    "m": defaultdict(set),
    "f": defaultdict(set),
}
for line in lines:
    if line.startswith("c\t"):
        parts = line.split("\t")
        classes[parts[1]] = parts[2]
    elif line.startswith("\tm\t") or line.startswith("\tf\t"):
        parts = line[1:].split("\t")
        members[parts[0]][parts[2]].add(parts[3])

unique_members = {
    kind: {name: next(iter(destinations)) for name, destinations in table.items() if len(destinations) == 1}
    for kind, table in members.items()
}

simple_ids: dict[str, str] = {}
for source, target in classes.items():
    old_parts = source.rsplit("/", 1)[-1].split("$")
    new_parts = target.rsplit("/", 1)[-1].split("$")
    for old, new in zip(old_parts, new_parts):
        if old.startswith("class_"):
            simple_ids[old] = new
for kind in ("m", "f"):
    for name, target_names in members[kind].items():
        if len(target_names) == 1:
            simple_ids[name] = next(iter(target_names))

qualified_class_pattern = re.compile(
    r"(?<![\w$/])(?:net\.minecraft\.(?:class_\d+\.)*class_\d+|net/minecraft/(?:class_\d+\$)*class_\d+)(?![\w$])"
)
identifier_pattern = re.compile(r"(?<![\w$])(?:class_\d+|method_\d+|field_\d+)(?![\w$])")

def replace_qualified_class(match: re.Match[str]) -> str:
    value = match.group(0)
    dotted = value.startswith("net.minecraft.")
    if dotted:
        tail = value[len("net.minecraft."):].split(".")
        source = "net/minecraft/" + tail[0] + (("$" + "$".join(tail[1:])) if len(tail) > 1 else "")
    else:
        source = value
    target = classes.get(source)
    if target is None:
        return value
    return target.replace("/", ".").replace("$", ".") if dotted else target

def replace_identifier(match: re.Match[str]) -> str:
    token = match.group(0)
    return simple_ids.get(token, token)

input_root = Path(args.input).resolve()
output_root = Path(args.output)
output_root.mkdir(parents=True, exist_ok=True)
file_count = 0
unresolved: set[str] = set()
for source in input_root.rglob("*.java"):
    text = source.read_text(encoding="utf-8")
    text = qualified_class_pattern.sub(replace_qualified_class, text)
    text = identifier_pattern.sub(replace_identifier, text)
    unresolved.update(re.findall(r"\b(?:class|method|field)_\d+\b", text))
    destination = output_root / source.relative_to(input_root)
    destination.parent.mkdir(parents=True, exist_ok=True)
    destination.write_text(text, encoding="utf-8", newline="\n")
    file_count += 1

print({
    "files": file_count,
    "classes_mapped": len(classes),
    "methods_mapped": len(unique_members["m"]),
    "fields_mapped": len(unique_members["f"]),
    "unresolved_intermediary_identifiers": sorted(unresolved),
})
