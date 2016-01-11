# Practicum
A system agnostic tool that allows power users to increase their dexterity and familiarity with the shortcuts and commands in their favorite programs. Practicum allows seasoned veterans and n00bs to master the subtle art of power using.


## Table of Contents
 - [Practicum](#practicum)
	- [Quick Setup](#quick-setup)
	- [Screenshots](#screenshots)
	- [Features](#features)
	- [API](#api)
	- [ToDo](#todo)
	- [FAQ / Troubleshooting](#faq--troubleshooting)
	- [License](#license)


## Quick Setup
Practicum.jar is an executable jar, so all it requires is a Java 8 runtime environment. The directory with Practicum.jar should also contain the HotKeys, Cmds, Typing, and Custom directories from this repo. Once these requirements have been met, Practicum should properly execute on Windows, Mac, and Linux.

## Screenshots

![Home Screen](Resources/HomeScreen.png?raw=true "Home Screen")

![Hotkey Example](Resources/HotkeyExample.png?raw=true "Hotkey Example")

## Features
Programs current supported by default are:
 * Sublime Text
 * Google Docs
 * Vivaldi
 * Vim

## API
Here is a json file from an example program.

	[
	{ 
		"lvl":"Window Manipulation",
		"type":"hotkeys",
		"set":
		[
			{"combo":"Ctrl+N", "desc":"New window"},
			{"combo":"Ctrl+Shift+N", "desc":"New private window"},
			{"combo":"Ctrl+Q", "desc":"Exit"}
		]
	},
	
	{
		"lvl":"Basics",
		"type":"cmds",
		"set":
		[
			{"combo":"q!", "desc":"Quit"},
			{"combo":"ss", "desc":"Save"},
			{"combo":"o", "desc":"Open"},
			{"combo":"neo", "desc":"New file"}
		]
	}
	]

## ToDo

* [x] Working demo
* [ ] Dynamic programs
* [ ] Touch typing 
* [ ] Cheatsheets
* [ ] Custom programs
* HotKey programs to add:
 * [ ] Netbeans
 * [ ] Windows
 * [ ] Terminator
* Cmd programs to add:
 * [ ] Ubuntu Cmd Line
 * [ ] AoE 2
 * [ ] Project Zomboid
 * [ ] Vintageous
 * [ ] Emmet
 * [ ] Vimium
 * [ ] Minecraft
* Touch Typing practice to add:
 * [ ] Alphanumerics
 * [ ] Ipsum Lorem
 * [ ] Foreign Languages
 * [ ] Classic Literature
 * [ ] Bible

## FAQ / Troubleshooting

* How do I add a hotkeys/cmds from a different program?
 * Check out our [API](#api)
* How do I modifiy the hotkeys/cmds for a specific program (Google Docs)?
 * This can be done by modifying the existing GoogleDocs.json in the HotKeys folder.
 * _NOTE:_ These changes will be lost on updates

## License

See [LICENSE](LICENSE)

<!---
Link References
-->

[nerd-fonts]:https://github.com/ryanoasis/nerd-fonts
