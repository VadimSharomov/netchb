For testing purposes, uniqueness is not enabled for the Manufacturer ID Code column, which looks like should be.
At each start, the database table is dropped-created. 
DB connection is in the file resources\application.properties. 

Since it is not described in the conditions, the file format is validated on the assumption that there should always be line start markers such as $1, $2, $3, $4, $5 and that they are located in this order. 
Also the validation was added for three main fields of the manufacturer object. 
Validation on the client side that the file is selected, that the file size is less than the specific size, and that only ".txt" type file is selected.
Added application property (maximum.manufacturer.file.size=1000) for limitation of the input manufacturer file size with default value 1000.

The types of tests were not mentioned, but important tests were added to check converting from the provided example files into the needed manufacturer object. 
With them always it can be checked if the file format has changed. 

Since nothing else is described, the error messages on the UI the same as in the logs.

One additional POST REST method was added "/add" for testing purpose or for possibility to add manually the manufacturer using for example postman.
Example of the body that method:
{
"manufacturerCode": "4356324",
"countryCode": "Az",
"firmName": "Firm 87654",
"streetAddress": "Street 76543",
"city": "Bremen",
"postalCode": "87654"
}