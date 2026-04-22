RODAR O PROJETO

javac -d bin -cp "lib/*" -sourcepath src src/ui/console/ConsoleApp.java src/ui/swing/SwingApp.java src/app/Main.java
java -cp "bin;lib/*" ui.swing.SwingApp
java -cp "bin;lib/\*" ui.console.ConsoleApp # terminal
