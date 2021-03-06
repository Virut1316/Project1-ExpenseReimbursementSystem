document.getElementById('pendingbtn').addEventListener('click',fillPending);
document.getElementById('resolvedbtn').addEventListener('click',fillResolved);

var pending;
var resolved;


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
    fillPending();
}catch(e){
    console.log('Something went wrong');
}


async function fillPending(){
	
		if(pending){
			fillPendingTable(pending);
			return;	
		}
		
        let sreq = await fetch('http://localhost:8080/project1-ERS/api/session');
        let sres = await sreq.json();
        let authorId = sres.userId;

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
		
        let sreq = await fetch('http://localhost:8080/project1-ERS/api/session');
        let sres = await sreq.json();


        let authorId = sres.userId;

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
                            <td>${element.type.type}</td>`;
		table.append(rowElement);
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

