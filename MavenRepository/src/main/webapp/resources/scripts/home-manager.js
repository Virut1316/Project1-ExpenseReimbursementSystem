document.getElementById('pendingbtn').addEventListener('click',fillPending);
document.getElementById('resolvedbtn').addEventListener('click',fillResolved);
document.getElementById('employeesbtn').addEventListener('click',fillEmployee);
var pending;
var resolved;
var employee;

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
                    Author
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
                    Author
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

var TableHeadEmployee = `<tr>
                    <th>
                    #
                    </th>
                    <th>
                    Username
                    </th>
                    <th>
                    First name
                    </th>
                    <th>
                    Last name
                    </th>
                    <th>
                    Email
                    </th>
                    <th>
                    
                    </th>
                    </tr>`;

try{
    fillPending();
}catch(e){
    console.log('Something went wrong');
}


async function fillPending(){

    if(pending){
        fillPendingTable(pending);
        return;	
    }

        let req = await fetch('http://localhost:8080/project1-ERS/api/reimbursement/all-pending');
        res = await req.json();
        pending = res;

        fillPendingTable(pending);
        //console.log(document.getElementById('4'));

}

async function fillEmployee(){

    if(employee){
        fillEmployeeTable(employee);
        return;	
    }

        let req = await fetch('http://localhost:8080/project1-ERS/api/user/all-employees');

        res = await req.json();
        employee = res;
        fillEmployeeTable(res);       

}

async function fillResolved(){

    if(resolved){
        fillResolvedTable(resolved);
        return;	
    }

        let req = await fetch('http://localhost:8080/project1-ERS/api/reimbursement/all-resolved');

        res = await req.json();
        resolved = res;
        fillResolvedTable(res);
}



function fillPendingTable(reimb){
    let pendingbtn = document.getElementById('pendingbtn');
    let resolvedbtn = document.getElementById('resolvedbtn');
    let employeesbtn = document.getElementById('employeesbtn');

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
                            <td>${element.author.username}</td>
                            <td>${element.description.substring(0,30)}` + (element.description.length>30?'...':'')+ `</td>
                            <td>${element.type.type}</td>
                            <td><button id="${element.id}" type="button" class="btn " style="background-color: #F25757; color:white; align-self: center;"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                          </svg></button></td>`;
		table.append(rowElement);
        document.getElementById(element.id).addEventListener('click',sendReimb);
    }
    }
    catch(e){
        console.log(e);
        let rowElement = document.createElement('tr');
        rowElement.innerHTML = `<th >No elements to display</th>`

    }
    employeesbtn.style.backgroundColor ='#EEEEEE'
    employeesbtn.style.color ='#003055';
    resolvedbtn.style.backgroundColor ='#EEEEEE'
    resolvedbtn.style.color ='#003055';
    pendingbtn.style.backgroundColor ='#003055'
    pendingbtn.style.color ='#EEEEEE';
    console.log(document.getElementById('4'));
}

function fillResolvedTable(reimb){
    let pendingbtn = document.getElementById('pendingbtn');
    let resolvedbtn = document.getElementById('resolvedbtn');
    let employeesbtn = document.getElementById('employeesbtn');
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
                            <td>${element.author.username}</td>
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
    employeesbtn.style.backgroundColor ='#EEEEEE'
    employeesbtn.style.color ='#003055';
    resolvedbtn.style.backgroundColor ='#003055'
    resolvedbtn.style.color ='#EEEEEE';
}

function fillEmployeeTable(reimb){
    let pendingbtn = document.getElementById('pendingbtn');
    let resolvedbtn = document.getElementById('resolvedbtn');
    let employeesbtn = document.getElementById('employeesbtn');
    document.getElementById('table-head').innerHTML = TableHeadEmployee;
    let table = document.getElementById('table-body');
    try{
    table.innerHTML = '';
    for(element of reimb){
		let rowElement = document.createElement('tr');
		rowElement.innerHTML = `  <th >${element.id}</th>
                            <td>${element.username}</td>
                            <td>${element.firstName}</td>
                            <td>${element.lastName}</td>
                            <td>${element.email}</td>
                            <td><button type="button" class="btn" style="background-color: #F25757; color:white; align-self: center;"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                            </svg></button></td>`
        
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
    employeesbtn.style.backgroundColor ='#003055'
    employeesbtn.style.color ='#EEEEEE';
    resolvedbtn.style.backgroundColor ='#EEEEEE'
    resolvedbtn.style.color ='#003055';
}

function sendReimb(e){

    try{localStorage.removeItem('reimb');}catch(e){}

    if(e.target.id){
        alert(e.target.id);
        localStorage.setItem('reimb', e.target.id);
    }
        
    else{
        alert(e.target.parentElement.id);
        localStorage.setItem('reimb', e.target.parentElement.id);
    }

    location.href = '../html/manager-approve-deny.html';	
        
}


