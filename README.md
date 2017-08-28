# Api Core Jar
The API Core project is created to handle the common tasks of each API such as authentication, authorization, database connections, etc.  This is not meant to be a complete resource and as development continues on other projects more features will be added to this library

##API
The API package holds the beans needed for (de)serialization 

###AbstractRecord
The AbstractRecord is meant to be extended by all objects that represent database records.  If it does not extend this record the Audit Utility will not accept it.

###Audit
This class is used to hold a line of audit data.  This class will never need to be used outside this library.

###RealmAccess
RealmAccess is part of the token introspection JSON object.

###ResourceAccess
ResourceAccess is also part of the taken

###TokenData
TokenData holds... token... data.

##DAO
The DAO package holds the DAOs used by JDBI to execute queries against the database.

###BaseDAO
BaseDAO should be extended by all other interface DAOs to provide logging.  All methods and fields need to be implemented.

##Util
The Utility package is where all the utility classes reside.

###Audit
The package where the audit utility lives

####AuditLogUtil
The AuditLogUtil holds a single method.  It takes as its arguments a DAO that extends BaseDAO, a record that extends AbstractRecord, and a string denoting the type of change. If the change is update, a diff is then calculated in the new information to be committed to the database and the record that already exists. If a field is marked in the bean with the "IgnoreInAudit" annotation it will not be included in the diff. 

####IgnoreInAudit
The definition of the annotation to ignore fields in the auditing process.

###Auth
This package holds the auth utilities.

####AuthenticationUtil
AuthenticationUtil is intended to run before every request to check if user is able to access the system.  It instantiates a Token object and calls the introspect method to check if the token is still valid.  If no authorization header is sent, an NPE is thrown and execution is stopped.  

####AuthorizationUtil
This utility gets a list of permissions that an endpoint is configured with from the database, adds the ":all" to it as well as a specific resource ID if one was specified in the request.  It then looks at the list of permissions from the introspected token and does a disjoint.  If the disjoint is true, then the token does not contain the necessary permissions.

####Token
Token class introspects a token and then stores it in the User class as a static field because... I don't know.  I really didn't see a better way to handle this. 

####User
Seemed easier than passing the instance of Token around after an introspection.

###ConsulUtil
Does all the consul stuff, like service discovery and KV reads. Init must be called before anything can be used.

###CoreConfigurationUtil
This needs to be initialised whenever an application starts.

###DatabaseUtil
Handles database connections

###EnvironmentUtil
Literally just gets the value of the "/etc/env" file or a specific file if you choose.

###FilterUtil
Contains a single method that accepts a json object and any other object.  The json object contains a whitelist of fields to keep and nulls out the rest.  If a field specified is not in the object nothing happens with it.  

###FormatUtil
Outputs a message in json format. Used primarily for one line messages, like errors, to be returned to the front end. 
