For testing purposes, uniqueness is not enabled for the Manufacturer ID Code column, which looks like should be.
At each start, the database table is dropped-created.
DB connection is in the file resources\application.properties.
Since it is not described in the conditions, the file format is validated on the assumption that there should always be line start markers such as $1, $2, $3, $4, $5 and that they are located in this order.
Also the validation was added for three main fields of the manufacturer object.
Validation on the client side that the file is selected, that file size is less that 1Kb, that only ".txt" file is selected.
The types of tests were not mentioned, but important tests were added to check converting from the provided example files into the needed manufacturer object. 
With them always it can be checked if the file format has changed.
Since nothing else is described, the error messages on the UI the same as in the logs.