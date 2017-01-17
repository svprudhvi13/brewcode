var input = [{
    "name": "Jane Smith",
    "location": "Boston",
    "employees": [
        {
            "name": "Mike Jones",
            "location": "Boston"
        },
        {
            "name": "Jane Michaels",
            "location": "Alpharetta"
        },
        {
            "name": "Donald Browm",
            "location": "Roseland",
            "employees": [
                {
                    "name": "Brian Smith",
                    "location": "Boston"
                },
                {
                    "name": "Tom Jones",
                    "location": "Alpharetta"
                }
            ]
        },
        {
            "name": "Jerry Whites",
            "location": "Roseland"
        }
    ]
}];

var emps= [];
function flattenTree( input,  emps){
	
}