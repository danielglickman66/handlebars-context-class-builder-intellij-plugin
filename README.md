#### Create a java handlebars context class from an handlebars html file.  



Currently supports 2 types of java classes:
1. a simple bean with standard getters and setters.
2. have the class receive an object in the constructor and setters receive
a function which operate on that object.


The class fields are currently of type Boolean if that handlebars variable with the same name starts with "is"(e.g "isSomething"), or otherwise a String.


#### Usage
use Generate action(Cmd N) while in a .html or .hbs file
