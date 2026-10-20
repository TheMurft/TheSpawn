# TheSpawn 2.0.2

Standalone extraction of the spawn system used by TheSmp, for Paper/Spigot 1.21.8.

## Commands

- `/spawn` — teleports after a configurable warmup; movement or damage cancels it.
- `/setspawn`
- `/thespawn reload`

## Permissions

- `thespawn.spawn` (everyone)
- `thespawn.setspawn` (operators)
- `thespawn.reload` (operators)

The location is stored in `plugins/TheSpawn/spawn.yml`. Messages, delay and movement tolerance are editable in `config.yml`.

Set `join-quit-messages.hide-vanilla` to `true` to hide Minecraft's default join and quit messages.

## Build

Requires Java 21 and Maven 3.9+. Run `mvn clean package`; the plugin will be created in `target/`.
