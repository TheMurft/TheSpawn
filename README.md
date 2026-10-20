# TheSpawn 🧭

<div align="center">
  <img src="https://img.shields.io/badge/Minecraft-1.21.8-62B47A?style=for-the-badge&logo=minecraft&logoColor=white" alt="Minecraft 1.21.8">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21">
  <img src="https://img.shields.io/badge/Platform-Paper%20%7C%20Spigot-5865F2?style=for-the-badge" alt="Paper and Spigot">
  <img src="https://img.shields.io/badge/Developer-TheMurft-F1C40F?style=for-the-badge" alt="Developer: TheMurft">
</div>

---

TheSpawn is a lightweight spawn manager for Paper and Spigot. Players can return to a configured spawn, while administrators control the location, warmup, and messages.

## ✨ Features

- ⏱️ **Teleport warmup:** Configure the delay before a player reaches spawn.
- 🛑 **Teleport cancellation:** Movement or damage cancels a pending teleport.
- ⚙️ **Configurable settings:** Adjust messages, delay, movement tolerance, and join/quit message behavior.
- 💾 **Persistent spawn:** The spawn location is stored in `plugins/TheSpawn/spawn.yml`.

## 🚀 Quick start

1. Build with `mvn clean package`.
2. Copy `target/TheSpawn-2.0.2.jar` to the server's `plugins/` folder.
3. Restart the server and set the location with `/setspawn`.

### Requirements

- Paper or Spigot 1.21.8
- Java 21
- Maven 3.9+ to build from source

## 🎮 Commands and permissions

| Command | Permission | Description |
| :--- | :--- | :--- |
| `/spawn` | `thespawn.spawn` | Teleport to the configured spawn. |
| `/setspawn` | `thespawn.setspawn` | Set the spawn to your current location. |
| `/thespawn reload` | `thespawn.reload` | Reload the plugin configuration. |

`thespawn.spawn` is available to everyone by default. The set-spawn and reload permissions default to operators.

## ⚙️ Configuration

Edit `plugins/TheSpawn/config.yml` to change the warmup, movement tolerance, messages, and join/quit message handling. Set `join-quit-messages.hide-vanilla` to `true` to hide Minecraft's default join and quit messages.

## 🧰 Build from source

```bash
mvn clean package
```

The plugin JAR is created in `target/`.

## 💬 Support

For questions or help, join the [Discord support server](https://discord.gg/6C5t995jC6).

---

Made with ❤️ by [TheMurft](https://github.com/TheMurft).
