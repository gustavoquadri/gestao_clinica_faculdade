RODAR O PROJETO

javac -d bin -cp "lib/_" -sourcepath src src/ui/console/ConsoleApp.java src/ui/swing/SwingApp.java src/app/Main.java
java -cp "bin;lib/_" ui.swing.SwingApp # GUI
java -cp "bin;lib/\*" ui.console.ConsoleApp # terminal
