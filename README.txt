Project: 
Silverrail Technical 

Description:
This is a project that starts a set of rest services to manipulate an alphanumeric string.
Possible manipulations are -
1. Sum all the digits in the string, e.g. anz68G9 will sum to 77
2. Save a string and retrieve it
3. Enable more than one "user" by using different browsers on the same computer

Installation:
Make sure you have maven installed.
Download the entire project into your workspace
Run "$mvn compile" to compile the project

Running the tests:
Run "$mvn test" to run the test JUnit suite


Usage:
GET/state returns a state string e.g. http://localhost:8080/getState?stringValue=mnT67Frs89

GET/sum returns the sum of the digits within the string e.g. http://localhost:8080/getState?stringValue=mnT67Frs8X9
where the summed amount will be in the amount field {"characters":"", "amount": xx}

POST/state appends the string passed as JSON to the existing state. The JSON string should have the format 
{"characters":"a", "amount":5} where the value appended to the state string will be "aaaaa"

Additional Notes:
It assumed that the maximum summed value is 2147483647 - constrained by integer.
Only 0 to the maximum value is allowed, no negative values have been catered for.
Only alphanumeric characters are allowed.
For the edge cases in the test suite no exception handling for exceeding the maximum int value has been provided

