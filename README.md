
![VindereCraft Core v0.0.2d](images/index-readme/logo-002d.png)
### The Foundation for Minecraft: Java Edition Servers running Paper

**--[ WARNING ]-->** Keep in mind, this is a DEVELOPMENT BUILD (hence the 'd' following the version) which means it may be unstable. Heck, it may not even load properly. No guarantees!

**--[ INFO ]-->** ***VindereCraft Core*** *was developed to act as the primary plugin for all other VindereCraft plugins to piggyback off of via API. It also handles most basic or administrative tasks, with functions like chat formatting or permissions being handled by a separate plugin.*
***Core*** *contains the functions to connect to databases and generate config files for the rest of the plugin suite to also access.*

## New Features
- `/core` command to view version of Core as well as other enabled VindereCraft Studio plugins. (See [Issue #5](https://github.com/VindereCraft/VindereCraft-Core/issues/5))

## Planned Features
- MySQL, MongoDB, and SQLite database integration for player data storage. (See [Issue #6](https://github.com/VindereCraft/VindereCraft-Core/issues/6), [Issue #2](https://github.com/VindereCraft/VindereCraft-Core/issues/2), [Issue #3](https://github.com/VindereCraft/VindereCraft-Core/issues/3))
- `/player` command to view information about a specific player, like their IP address and locale.  Features will be primarily suited for server administration. (See [Issue #4](https://github.com/VindereCraft/VindereCraft-Core/issues/4))
- `/home` command to set and go to a players set home. Allows for customizable amounts via permissions. (See [Issue #7](https://github.com/VindereCraft/VindereCraft-Core/issues/7))
- `/spawn` command to return to the server spawn. Also includes functionality to automatically spawn new players there as well as return people there on death if no home is set. (See [Issue #8](https://github.com/VindereCraft/VindereCraft-Core/issues/8))