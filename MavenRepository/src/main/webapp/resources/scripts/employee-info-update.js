document.getElementById('info-form').addEventListener('submit',updateInfo);
document.getElementById('password-form').addEventListener('submit',updatePassword);

fillUserData();


async function updateInfo(e){
    e.preventDefault();

    let firstNameT = document.getElementById('first-name').value;
    let lastNameT = document.getElementById('last-name').value;
    let username = document.getElementById('username').value;
    let email = document.getElementById('email').value;


    let sreq = await fetch('http://localhost:8080/project1-ERS/api/session');
        let sres = await sreq.json();


        let userId = sres.userId;

        userData = {
            userId,
            "first-name":firstNameT,
            "last-name":lastNameT,
            username,
            email
        }



        try{
        let req = await fetch('http://localhost:8080/project1-ERS/api/user/update-info',{
            method: 'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify(userData)


        });

        res = await req.json();

        if(res.code==403)
            throw res.message;

            alert("Changes Saved");
    }
    catch(e){
        alert("Username or Email already taken");
        fillUserData();
    }

    
}

async function fillUserData(){
    let pendingbtn = document.getElementById('pendingbtn');
    let resolvedbtn = document.getElementById('resolvedbtn');

        let sreq = await fetch('http://localhost:8080/project1-ERS/api/session');
        let sres = await sreq.json();


        let userId = sres.userId;

        let parameters = {
            userId
        }


        let req = await fetch('http://localhost:8080/project1-ERS/api/user/get-user-id',{
            method: 'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify(parameters)


        });

        res = await req.json();

        fillSpaces(res);
}

async function updatePassword(e){
    e.preventDefault();

    let oldPassword = document.getElementById('old-password');
    let newPassword = document.getElementById('new-password');
    let newPassword2 = document.getElementById('new-password-confirm');



    let sreq = await fetch('http://localhost:8080/project1-ERS/api/session');
        let sres = await sreq.json();


        let userId = sres.userId;

        let userData = {
            userId,
            "new-password":newPassword2.value
        }




        try{

        if(!(newPassword.value==newPassword2.value))
            throw "New passwords doesn't match";

        
        
        let requser = await fetch('http://localhost:8080/project1-ERS/api/user/get-user-id',{
            method: 'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify(userData)
    
    
        });

            resuser = await requser.json();

            if(resuser.password != oldPassword.value)
                throw "Incorrect password";

            if(resuser.password==newPassword2.value)
                throw "New password can't be the same as the old one";


        let req = await fetch('http://localhost:8080/project1-ERS/api/user/update-password',{
            method: 'POST',
            headers: {
                'Content-Type':'application/json'
            },
            body: JSON.stringify(userData)


        });

        res = await req.json();

        if(res.code==403)
        throw res.message;

        alert("New password updated");

    }
    catch(e){
        alert(e);
        fillUserData();
    }

    newPassword.value = newPassword2.value =oldPassword.value ="";
    

}

function fillSpaces(data){

    let firstNameT = document.getElementById('first-name');
    let lastNameT = document.getElementById('last-name');
    let usernameT = document.getElementById('username');
    let emailT = document.getElementById('email');
    
    firstNameT.value = data.firstName;
    lastNameT.value = data.lastName;
    usernameT.value = data.username;
    emailT.value = data.email;

}