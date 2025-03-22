
![](https://i.imgur.com/w7729G1.gif)

[![License][License: AGPL v3]][license-url] [![License][Shield: Fork]][fork-url]

### Information:

Fork of [2009scape-item-definition-editor](https://gitlab.com/2009scape/tools/2009scape-item-definition-editor)

Never make a merge request to the main 2009scape repository after using this tool

### Libraries / Dependencies:

This project use following libraries:

- **rs-cache-library**: [github repository](https://github.com/Displee/rs-cache-library)
- The [names.dat](data/names.dat) file is provided from [rune-server thread](https://rune-server.org/threads/634-cache-file-hash-names.705673/). All credit to these gentlemen.

### How to Build:

To build the project, you will need to have Gradle installed on your system. Follow these steps:

1. Clone the repository to your local machine:

   ```bash
   git clone https://gitlab.com/rs2-emu/530-editor.git
   cd 530-editor
   ```

2. If you do not have Gradle installed, download and install it from the [official site](https://gradle.org/install/).

3. Build the tool using Gradle:

   ```bash
   gradle build
   ```

   This will compile the project and create a `.jar` file in the `build/libs/` directory.

4. Run the tool:

   After building, navigate to the `build/libs/` folder and execute the following command:

   ```bash
   java -jar 530-editor.jar
   ```

### Additional Information:

For detailed documentation and troubleshooting, please refer to the repository's issues and discussions.

### License:

```
Licensed under the GNU Affero General Public License (AGPL) v3.0.
You may obtain a copy of the License at:
https://www.gnu.org/licenses/agpl-3.0.html

2009scape Item Definition Editor
Copyright (C) 2022  2009Scape / Tools
```

[License: AGPL v3]: https://img.shields.io/badge/License-AGPL%20v3-khaki.svg

[license-url]: https://www.gnu.org/licenses/agpl-3.0.en.html

[Shield: Fork]: https://img.shields.io/badge/repository-fork-tan

[fork-url]: https://gitlab.com/2009scape/tools/2009scape-item-definition-editor
