document.getElementById('approve').addEventListener('click',approveReimb);
document.getElementById('deny').addEventListener('click',denyReimb);

fillReimData();

async function fillReimData(){

        
        let reimbursementId = localStorage.getItem('reimb'); // get reimbursement selected

        let parameters = {
            reimbursementId
        }


        let req = await fetch('http://localhost:8080/project1-ERS/api/reimbursement',{
            method: 'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify(parameters)


        });

        res = await req.json();

        fillSpaces(res);
}

function fillSpaces(data){

    let id = document.getElementById('idR');
    let amount = document.getElementById('amount');
    let author = document.getElementById('author');
    let submitted = document.getElementById('submitted');
    let type = document.getElementById('typeR');
    let description =document.getElementById('description');
    
    dollar = data.amount.toString().substring(0,data.amount.toString().length-2);
	cents = data.amount.toString().substring(data.amount.toString().length-2,data.amount.toString().length);

    id.value = data.id;
    amount.value = `${dollar}.${cents}$`;
    author.value = data.author.username +' ('+data.author.firstName + ' ' + data.author.lastName+')'
    submitted.value = data.submitted;
    type.value = data.type.type;
    description.value = data.description;

}

async function denyReimb(){
    let reimbursementId = document.getElementById('idR').value;

    try{
    let sreq = await fetch('http://localhost:8080/project1-ERS/api/session');
    let sres = await sreq.json();

    let resolverId = sres.userId;

    let statusId = 2;

    let parameters = {
        reimbursementId,
        resolverId,
        statusId
    }


    let req = await fetch('http://localhost:8080/project1-ERS/api/reimbursement/approve-or-deny',{
        method: 'POST',
        headers: {
            'Content-Type':'application/json'
        },
        body: JSON.stringify(parameters)


    });
    let res = await req.json();

        if(res.code == 403)
            throw "A problem ocurred, please try again later"
        else
            {
                alert('Reimbursement request denied');
                localStorage.removeItem('reimb');
                location.href = '../html/manager-home-page.html';	
            }

    }
    catch(e){
        alert(e);
    }

}

async function approveReimb(){
    let reimbursementId = document.getElementById('idR').value;

    try{
    let sreq = await fetch('http://localhost:8080/project1-ERS/api/session');
    let sres = await sreq.json();

    let resolverId = sres.userId;

    let statusId = 3;

    let parameters = {
        reimbursementId,
        resolverId,
        statusId
    }


    let req = await fetch('http://localhost:8080/project1-ERS/api/reimbursement/approve-or-deny',{
        method: 'POST',
        headers: {
            'Content-Type':'application/json'
        },
        body: JSON.stringify(parameters)


    });
    let res = await req.json();

        if(res.code == 403)
            throw "A problem ocurred, please try again later"
        else
            {
                alert('Reimbursement request approved');
                localStorage.removeItem('reimb');
                location.href = '../html/manager-home-page.html';	
            }

    }
    catch(e){
        alert(e);
    }

}

