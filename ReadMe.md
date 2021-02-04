This project is a reactive web application that queries data from a neo4j graph database. The project uses the reactive session from the neo4j driver to run the cypher query.

### Using this project
You must have Neo4j installed and running in your environment. You can change the Neo4j settings in the Neo4jService.java file.
The web server is configured to run on port 8088 and this can be changed in application.properties.

Start the spring boot application using intelliJ or VS Code.
Then navigate to http://localhost:8088/query.

If your neo4j database is setup and has some nodes then they should appear in the browser

