document.getElementById('logoutbtn').addEventListener('click', logout);
document.getElementById('accountbtn').addEventListener('click', ()=>{location.href = '../html/employee-info-update.html';});

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