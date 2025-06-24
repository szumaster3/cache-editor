import json
import requests
import re
import time
import threading
import sys

npc_config = {
    "id": "",
    "name": "",
    "examine": "",
    #"melee_animation": "0",
    #"range_animation": "0",
    #"magic_animation": "0",
    #"death_animation": "0",
    #"defence_animation": "0",
    #"combat_style": "0",
    #"protect_style": "0",
    #"clue_level": "0",
    #"combat_audio": "0,0,0",
    #"bonuses": "0,0,0",
    #"force_talk": "",
    #"spawn_animation": "0",
    #"lifepoints": "1",
    "attack_level": "1",
    "strength_level": "1",
    "defence_level": "1",
    "range_level": "1",
    #"movement_radius": "0",
    #"agg_radius": "0",
    #"attack_speed": "4",
    #"poison_amount": "0",
    #"respawn_delay": "60",
    #"start_gfx": "0",
    #"projectile": "0",
    #"end_gfx": "0",
    #"weakness": "0",
    #"slayer_task": "0",
    #"start_height": "0",
    #"prj_height": "0",
    #"end_height": "0",
    #"spell_id": "0",
    #"death_gfx": "0",
    "magic_level": "1",
    #"slayer_exp": "0.0",
    "safespot": "null",
    "aggressive": "false",
    #"poisonous": "false",
    #"poison_immune": "false",
    #"facing_booth": "false",
    #"can_tolerate": "false",
    #"water_npc": "false"
}

stop_flag = False

def stop_listener():
    """Listen for 'stop' command to terminate script."""
    global stop_flag
    while True:
        line = sys.stdin.readline()
        if line.strip().lower() == "stop":
            print("\nStops...")
            stop_flag = True
            break


def get_stats(name):
    # Replace URL
    url = f"https://<wiki_link>.wiki/w/{name.replace(' ', '_')}?action=edit"
    print(f"Fetching data for NPC: {name}")
    result = {
        "examine": None,
        "attack": None,
        "speed": None,
        "defence": None,
        "strength": None,
        "ranged": None,
        "magic": None,
        "aggressive": None,
    }
    try:
        response = requests.get(url, timeout=10)
        if response.status_code == 200:
            text = response.text

            match_examine = re.search(r"\|examine\s*=\s*(.+)", text)
            if match_examine:
                examine = match_examine.group(1).strip()
                examine = re.sub(r"\}\}.*", "", examine)
                result["examine"] = examine
                print(f" ->  Found examine: {examine}")
            else:
                match_examine1 = re.search(r"\|examine1\s*=\s*(.+)", text)
                if match_examine1:
                    examine1 = match_examine1.group(1).strip()
                    examine1 = re.sub(r"\}\}.*", "", examine1)
                    result["examine"] = examine1
                    print(f" ->  Found examine1 as examine: {examine1}")


            match_attack = re.search(r"\|attack\s*=\s*(\d+)", text)
            if match_attack:
                result["attack"] = int(match_attack.group(1))
                print(f" ->  Found attack: {result['attack']}")

            match_speed = re.search(r"\|speed\s*=\s*(\d+)", text)
            if match_speed:
                result["speed"] = int(match_speed.group(1))
                print(f" ->  Found speed: {result['speed']}")

            match_defence = re.search(r"\|defence\s*=\s*(\d+)", text)
            if match_defence:
                result["defence"] = int(match_defence.group(1))
                print(f" ->  Found defence: {result['defence']}")

            match_strength = re.search(r"\|strength\s*=\s*(\d+)", text)
            if match_strength:
                result["strength"] = int(match_strength.group(1))
                print(f" ->  Found strength: {result['strength']}")

            match_ranged = re.search(r"\|ranged\s*=\s*(\d+)", text)
            if match_ranged:
                result["ranged"] = int(match_ranged.group(1))
                print(f" ->  Found ranged: {result['ranged']}")

            match_magic = re.search(r"\|magic\s*=\s*(\d+)", text)
            if match_magic:
                result["magic"] = int(match_magic.group(1))
                print(f" -> Found magic: {result['magic']}")

            match_aggressive = re.search(
                r"\|aggressive\s*=\s*(true|false)", text, re.IGNORECASE
            )
            if match_aggressive:
                result["aggressive"] = match_aggressive.group(1).lower()
                print(f" ->  Found aggressive: {result['aggressive']}")
        else:
            print(f"Site return status: {response.status_code} for: {name}")
    except Exception as e:
        print(f"Error for {name}: {e}")

    return result


def main():
    global stop_flag

    thread = threading.Thread(target=stop_listener, daemon=True)
    thread.start()

    print("Load npc config...")
    with open("npc_configs.json", "r", encoding="utf-8") as file:
        npc_list = json.load(file)

    updated_fields = 0
    wiki_fetched = 0
    missing_data = []

    total = len(npc_list)
    print(f"Loaded {total} NPC ids.")
    print("Type 'stop' to save & exit.")

    for idx, npc in enumerate(npc_list, 1):
        if stop_flag:
            print("\nStop requested, exiting...")
            break

        print(
            f"\nProcess NPC {idx}/{total}: {npc.get('name', 'Unknown')} (ID: {npc.get('id', 'N/A')})"
        )

        for key, default in npc_config.items():
            if key not in npc or npc[key] in ["", "null", None]:
                npc[key] = default
                updated_fields += 1

        if npc["name"] != "":
            wiki_data = get_stats(npc["name"])
            time.sleep(1.0)

            if wiki_data["examine"] and wiki_data["examine"] not in ["", "Unknown"]:
                npc["examine"] = wiki_data["examine"]
                wiki_fetched += 1
                print("  Examine updated.")
            else:
                print("  No examine found.")

            # update stats.
            if wiki_data["attack"] is not None:
                npc["attack_level"] = str(wiki_data["attack"])
                print(f" ->  attack_level updated to {npc['attack_level']}")
            if wiki_data["speed"] is not None:
                npc["attack_speed"] = str(wiki_data["speed"])
                print(f" ->  attack_speed updated to {npc['attack_speed']}")
            if wiki_data["defence"] is not None:
                npc["defence_level"] = str(wiki_data["defence"])
                print(f" ->  defence_level updated to {npc['defence_level']}")
            if wiki_data["strength"] is not None:
                npc["strength_level"] = str(wiki_data["strength"])
                print(f" ->  strength_level updated to {npc['strength_level']}")
            if wiki_data["ranged"] is not None:
                npc["range_level"] = str(wiki_data["ranged"])
                print(f" ->  range_level updated to {npc['range_level']}")
            if wiki_data["magic"] is not None:
                npc["magic_level"] = str(wiki_data["magic"])
                print(f" ->  magic_level updated to {npc['magic_level']}")
            if wiki_data["aggressive"] is not None:
                npc["aggressive"] = wiki_data["aggressive"]
                print(f" ->  aggro updated to {npc['aggressive']}")

            missing = []
            if not npc["examine"] or npc["examine"] in ["", "Unknown"]:
                missing.append("examine")
            for key in [
                "attack_level",
                "attack_speed",
                "defence_level",
                "strength_level",
                "range_level",
                "magic_level",
            ]:
                if key not in npc or npc[key] in ["", "null", None]:
                    missing.append(key)

            if missing:
                missing_data.append(npc["name"])

    with open("npc_configs_UPDATED.json", "w", encoding="utf-8") as file:
        json.dump(npc_list, file, indent=2, ensure_ascii=False)

    if missing_data:
        with open("missing_data.txt", "w", encoding="utf-8") as file:
            for name in sorted(set(missing_data)):
                file.write(name + "\n")

    print(f"\nDone.")
    print(f"Empty fields filled: {updated_fields}")
    print(f"Examine fetched: {wiki_fetched}")
    print(f"NPCs with missing data: {len(set(missing_data))} (missing_data.txt)")


if __name__ == "__main__":
    main()
