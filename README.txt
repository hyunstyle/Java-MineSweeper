**********************************************************************************************

<Mine Sweeper Project>


12123215 Yu Sang Hyeon
12161538 Kim Min Kyu


used Eclipse Version : Eclipse Neon 2


  *--special info--*

1. we used 1 external library for UI(Look and Feel).
   the library is contained in "lib" folder, and "de" folder.
   the name of library is called "synthetica", from www.javasoft.de/synthetica/
   it's free api for no commercial application.
   more specifically, in the application, THEME menu -> BLACKSTAR theme is the
   library's Look and Feel.
   and also basic THEMEs(Metal, Nimbus) can work.[extra-changing theme]

2. we added PAUSE, THEME in menu. [extra function]

3. in SAVE GAME, our application can SET USER-DEFINED FILE NAME.
   (basic JFileChooser doesn't support arbitrary file name setting.)

4. in menu->help->About MineSweeper, we handled it by a LINK.
   when user clicks About MineSweeper, 
   [http://www.wikihow.com/Play-Minesweeper] this page is toggled.
   this page explains how to play mine sweeper in detail.

5. to manage 3 options(Always save game on exit, Always continue saved games, 
   allow question marks), "setting.txt" file is automatically created and 
   properly modified when option change detected.

6. to implement "Always continue saved games" option, "lastSavedGamePath.txt" file
   is automatically created when user saves game. according to this Path, 
   "Always continue saved games" option can be implemented.

7. to manage statistics, "statistics.txt" file is automatically created and updated
   when each game is finished or overed.

** although user deletes those files, automatically re-created. but data is not recovered.
    
8. we added several shortcuts except mentioned on MinesweeperProject.pdf.
   especially, about "PAUSE", "THEME", "EXIT", "TOGGLE SHORTCUT".. and so on. [extra]


 Thank you!

**********************************************************************************************
