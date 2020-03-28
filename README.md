# online-shopping
## Project Description
This is a complete web application project for electronic devices online shopping. There are four main parts to the application: shopping, registering new user, checkout, and administration. 

#### Project Tools
It's developed using Spring MVC, Spring Security, Spring Webflow, Hibernate, Bootstrap and JQuery.

#### Database Usage
For the Database, this project using H2Database for simplicity of installation and you can build the table throw the Hibernate itself or by running the databaseQueries.sql file that located under the shoppingbackend folder. And by using the abovementioned sql file, you will be guarantee that a sample data will be inserted into the created tables.

#### Thanks for the original Author
Here we have to thank Khozema Nullwala on his really great job for building the project as a reference for everybody and you can find the original project in the following GitHub: https://github.com/khozema-nullwala/online-shopping link.

#### Enhancements And Bug Fixing
All what I did is doing some descent amount of enhancements on the project as follows:

	1- Applying the AOP (Aspect oriented Programming) feature on the project by adding the related dependencies in pom file and do changes in dispatcher-servlet.xml to log all CRUD operations. You can find that in the following new class "net.hka.onlineshopping.aop.DaoCRUDAspect".
	
	2- Add missing title in register and chekout webflows by defining flow scope title variable in both xml files for each webflow.
	
	3- Solving the issue in register webflow confirm "signup-confirm.jsp" page by hiding the navbar in this page.
	
	4- Solving the miss format "order-confirm.jsp" file of chekout webflow and add this page to work on shred folder files.
	
	5- Make some code enhancements on CartService and CartController classes.
	
	6- Apply the design principle "program to interface" by adding new class "net.hka.onlineshopping.serviceimpl.CartServiceImpl". And converting the original class CartService to an interface.
	
	7- Create a new package "net.hka.onlineshopping.controller.security" to hold the moved and renamed the GlobalController advice to the new name GlobalAuthenticationHandler.
	
	8- Change in the format of some RequestMapping URL such as in ManagementController class the RequestMapping URL for the manageProductEdit method was @RequestMapping("/{id}/product") and I changed it to ("/product/{id}").
	
	9- Change the name of myapp.js to app.js and myapp.css to app.css.
	
	10- Add the missing comments in java files, app.js, app.css, checkout-flow.xml, signup-flow.xml, and JSP files.
	
	11- Updating in the Project_Steps.txt file by adding the missing steps and parts.
	
	12- Make a design enhancement by Throw the ProductNotFoundException from the DAO file.
	
	13- Define new exception for cart line and throw it from CartLineDAO.
	
	14- Use Java enum to handle user roles in all application by adding new UserRole enum in dto package. 