document.getElementById('pendingbtn').addEventListener('click',fillPending);
document.getElementById('resolvedbtn').addEventListener('click',fillResolved);

var pending;
var resolved;
var username ;


var TableHeadPending = `<tr>
                    <th>
                    #
                    </th>
                    <th>
                    Amount
                    </th>
                    <th>
                    Submitted
                    </th>
                    <th>
                    Description
                    </th>
                    <th>
                    Type
                    </th>
                    <th>
                    
                    </th>
                    </tr>`;

var TableHeadResolved = `<tr>
                    <th>
                    #
                    </th>
                    <th>
                    Amount
                    </th>
                    <th>
                    Submitted
                    </th>
                    <th>
                    Resolved
                    </th>
                    <th>
                    Resolver
                    </th>
                    <th>
                    Description
                    </th>
                    <th>
                    Type
                    </th>
                    <th>
                    Status
                    </th>
                    </tr>`;


try{
    actualUsername = localStorage.getItem('username');
    console.log(actualUsername);
    fillPending();
}catch(e){
    console.log('Something went wrong');
}


async function fillPending(){
	
		if(pending){
			fillPendingTable(pending);
			return;	
		}

        let username = {
            'username' : actualUsername
        }
		
        let sreq = await fetch('http://localhost:8080/project1-ERS/api/user/get-username',{
            method: 'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify(username)


        });
        let sres = await sreq.json();
        console.log(sres);
        let authorId = sres.id;

        let parameters = {
            authorId
        }


        let req = await fetch('http://localhost:8080/project1-ERS/api/reimbursement/pending-requests',{
            method: 'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify(parameters)


        });

        res = await req.json();

		pending = res;
        fillPendingTable(res);

}

async function fillResolved(){
	
		if(resolved){
			fillResolvedTable(resolved);
			return;	
		}
		
        let username = {
            'username' : actualUsername
        }
		
        let sreq = await fetch('http://localhost:8080/project1-ERS/api/user/get-username',{
            method: 'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify(username)


        });
        let sres = await sreq.json();
        console.log(sres);
        let authorId = sres.id;

        let parameters = {
            authorId
        }


        let req = await fetch('http://localhost:8080/project1-ERS/api/reimbursement/resolved-requests',{
            method: 'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify(parameters)


        });

        res = await req.json();
		resolved = res;
        fillResolvedTable(res);

}



function fillPendingTable(reimb){
	let pendingbtn = document.getElementById('pendingbtn');
	let resolvedbtn = document.getElementById('resolvedbtn');
    document.getElementById('table-head').innerHTML = TableHeadPending;
    let table = document.getElementById('table-body');
    try{
    
    table.innerHTML = '';
    for(element of reimb){
        dollar = element.amount.toString().substring(0,element.amount.toString().length-2);
		cents = element.amount.toString().substring(element.amount.toString().length-2,element.amount.toString().length);
		let rowElement = document.createElement('tr');
		rowElement.innerHTML = `  <th >${element.id}</th>
                            <td>${dollar}.${cents}$</td>
                            <td>${element.submitted}</td>
                            <td>${element.description.substring(0,30)}` + (element.description.length>30?'...':'')+ `</td>
                            <td>${element.type.type}</td>
                            <td><button id="${element.id}" type="button" class="btn " style="background-color: #F25757; color:white; align-self: center;"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                          </svg></button></td>`;
                            ;
		table.append(rowElement);
        document.getElementById(element.id).addEventListener('click',sendReimb);
    }
    }
    catch(e){
        let rowElement = document.createElement('tr');
        rowElement.innerHTML = `<th >No elements to display</th>`

    }
        resolvedbtn.style.backgroundColor ='#EEEEEE'
        resolvedbtn.style.color ='#003055';
        pendingbtn.style.backgroundColor ='#003055'
        pendingbtn.style.color ='#EEEEEE';
}

function fillResolvedTable(reimb){
	
    let pendingbtn = document.getElementById('pendingbtn');
    let resolvedbtn = document.getElementById('resolvedbtn');
	
    document.getElementById('table-head').innerHTML = TableHeadResolved;
    let table = document.getElementById('table-body');
    try{
	let dollar=0;
	let cents=0;
    table.innerHTML = '';
    for(element of reimb){
		//${element.amount.substring(0,element.amount.length-2)}.${element.amount.substring(element.amount.length-2,element.amount.length)}$
		dollar = element.amount.toString().substring(0,element.amount.toString().length-2);
		cents = element.amount.toString().substring(element.amount.toString().length-2,element.amount.toString().length);
		let rowElement = document.createElement('tr');
		rowElement.innerHTML = `  <th >${element.id}</th>
                            <td>${dollar}.${cents}$</td>
                            <td>${element.submitted}</td>
                            <td>${element.resolved}</td>
                            <td>${element.resolver.username}</td>
                            <td>${element.description.substring(0,30)}` + (element.description.length>30?'...':'')+ `</td>
                            <td>${element.type.type}</td>
                            <td ` +(element.status.status=='Rejected'?'style="color:red;"':'style="color:green;"') + `>${element.status.status}</td>`;
		table.append(rowElement);
    }
    
    }
    catch(e){
	    console.log(e);
        let rowElement = document.createElement('tr');
        rowElement.innerHTML = `<th >No elements to display</th>`
    }
    pendingbtn.style.backgroundColor ='#EEEEEE'
    pendingbtn.style.color ='#003055';
    resolvedbtn.style.backgroundColor ='#003055'
    resolvedbtn.style.color ='#EEEEEE';
}

function sendReimb(e){

    try{localStorage.removeItem('reimb');}catch(e){}

	
    if(e.target.id)
        localStorage.setItem('reimb', e.target.id);
    else if (e.target.parentElement.id)
        localStorage.setItem('reimb', e.target.parentElement.id);
    else
        localStorage.setItem('reimb', e.target.parentElement.parentElement.id);
    

    location.href = '../html/manager-approve-deny.html';	
        
}
