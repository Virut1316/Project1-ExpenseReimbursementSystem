checkSession();

async function checkSession(){

    let sreq = await fetch('http://localhost:8080/project1-ERS/api/session');


    if(sreq.status==404)
        {

            location.href = '../html/login-page.html';	

        }


}