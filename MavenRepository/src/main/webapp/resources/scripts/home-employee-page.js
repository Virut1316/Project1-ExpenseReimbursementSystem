document.getElementById('pendingbtn').addEventListener('click',fillPending);
document.getElementById('resolvedbtn').addEventListener('click',fillResolved);
document.getElementById('logoutbtn').addEventListener('click', logout);
document.getElementById('accountbtn').addEventListener('click', ()=>{location.href = '../html/employee-info-update.html';});

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

    let pendingbtn = document.getElementById('pendingbtn');
    let resolvedbtn = document.getElementById('resolvedbtn');

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

        fillPendingTable(res);

        resolvedbtn.style.backgroundColor ='#EEEEEE'
        resolvedbtn.style.color ='#003055';
        pendingbtn.style.backgroundColor ='#003055'
        pendingbtn.style.color ='#EEEEEE';

}

async function fillResolved(){

    let pendingbtn = document.getElementById('pendingbtn');
    let resolvedbtn = document.getElementById('resolvedbtn');

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

        fillResolvedTable(res);

    pendingbtn.style.backgroundColor ='#EEEEEE'
    pendingbtn.style.color ='#003055';
    resolvedbtn.style.backgroundColor ='#003055'
    resolvedbtn.style.color ='#EEEEEE';

}

async function logout(){

        let sreq = await fetch('http://localhost:8080/project1-ERS/api/log-out');
        let sres = await sreq.json();

        if(sreq.status==200)
            {

                location.href = '../html/login-page.html';	

            }
        else{
                alert("Something went wrong");
                location.href = '../html/login-page.html';	

            }

}



function fillPendingTable(reimb){
    document.getElementById('table-head').innerHTML = TableHeadPending;
    let table = document.getElementById('table-body');
    table.innerHTML = '';
    for(element of reimb){
		let post = document.createElement('tr');
		post.innerHTML = `  <th >${element.id}</th>
                            <td>${element.amount}</td>
                            <td>${element.submitted}</td>
                            <td>${element.description}</td>
                            <td>${element.type.type}</td>`;
		table.append(post);
    }
}

function fillResolvedTable(reimb){
    document.getElementById('table-head').innerHTML = TableHeadResolved;
    let table = document.getElementById('table-body');
    table.innerHTML = '';
    for(element of reimb){
		let post = document.createElement('tr');
		post.innerHTML = `  <th >${element.id}</th>
                            <td>${element.amount}</td>
                            <td>${element.submitted}</td>
                            <td>${element.resolved}</td>
                            <td>${element.resolver.username}</td>
                            <td>${element.description}</td>
                            <td>${element.type.type}</td>
                            <td ` +(element.status.status=='Rejected'?'style="color:red;"':'style="color:green;"') + `>${element.status.status}</td>`;
		table.append(post);
    }
}

