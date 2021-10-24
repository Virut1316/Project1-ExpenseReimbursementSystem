let form = document.getElementById("input-form");
form.addEventListener('submit', login);


async function login (e){
    e.preventDefault();

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let user = {
        username,
        password
    }

    

    let req = await fetch('http://localhost:8080/project1-ERS/api/login',{
        method: 'POST',
        headers: {
            'Content-Type':'application/json'
        },
        body: JSON.stringify(user)


    });
    //console.log(req);

    let res = await req.json();

    console.log(res);

    if(res.code == 403){
        console.log(res.message);
        //add logic of displaying error info to user
    }
    else
       location.href = '../html/home.html';


}
