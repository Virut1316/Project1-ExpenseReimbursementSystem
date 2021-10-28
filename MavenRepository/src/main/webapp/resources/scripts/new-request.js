document.getElementById('request-form').addEventListener('submit',createRequest);


async function createRequest(e){
    e.preventDefault();
    try{
    let amount = document.getElementById('amount').value;
    let description = document.getElementById('description').value.trim();
    let typeId = document.getElementById('r-type').value;

    let sreq = await fetch('http://localhost:8080/project1-ERS/api/session');
    let sres = await sreq.json();

    if((amount.indexOf('.')<=-1)||(amount.indexOf('.')!=(amount.length-3)))
        throw "Incorrect money format";
    
    amount = amount*100;

    if(amount<=0)
        throw "Amount can't be 0"

    let userId = sres.userId;

    userData = {
        userId,
        amount,
        description,
        typeId
    }



    console.log(description);

    let req = await fetch('http://localhost:8080/project1-ERS/api/reimbursement/create',{
        method: 'POST',
        headers: {
            'Content-Type':'application/json'
        },
        body: JSON.stringify(userData)


    });

        res = req.json();

        if(res.code==403)
            throw "Problem while creating a new request, try again later"

        alert("Request successfully created");

    }
    catch(e){
        alert(e);
    }
    
    document.getElementById('amount').value = '';
    document.getElementById('description').value = '';
    document.getElementById('r-type').selectedIndex = 0 ;
}



