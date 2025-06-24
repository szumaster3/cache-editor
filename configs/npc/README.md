### Npc config updater

[![License][License: AGPL v3]][license-url]

***

Python script `npc_stats_update.py` updates NPC JSON data by fetching data from wiki.
This helps preserve and complete NPC data for personal or development use.

#### Use

- Put `npc_configs.json` in the script folder.
- Run: `python npc_config_update.py`
- Output: `npc_configs_UPDATED.json`
- Missing data NPCs saved to `missing_data.txt`.
- Type **`stop`** for safely save and exit.

#### Feature

- Loads NPC data and fills missing defaults.
- Fetches data info.
- Logs progress to console.
- Saves updated data to json and logs entries to txt.

#### Notes

- Ultra basic, educational script.
- Requires `requests`
- **Rate-limited**

#### Disclaimer

For **educational purposes only**.  
Respect RuneScape Wiki's terms and server limits.  
Not affiliated with Jagex or RuneScape Wiki.

---

*This repository is a knowledge base and code reference.*


[License: AGPL v3]: https://img.shields.io/badge/License-AGPL%20v3-khaki.svg

[license-url]: https://www.gnu.org/licenses/agpl-3.0.en.html

[Shield: Fork]: https://img.shields.io/badge/repository-fork-tan

[fork-url]: https://gitlab.com/2009scape/tools/2009scape-item-definition-editor