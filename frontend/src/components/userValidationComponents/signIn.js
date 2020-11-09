import "./userValidationStyle.css";
import { useState } from "react";
import {Link} from 'react-router-dom';
import {useHistory} from 'react-router-dom';


function makeRequest(email, password, history) {
    let data = {"email": email, "password": password};
    console.log(data);
    console.log('checking server');

    fetch('http://localhost:8080/user/login?email=' + email + "&password=" + password)
        .then((response) => {
            if (response.status === 204) {
                console.log("successful log in");
                history.push({
                    pathname: '/toDoList',
                    state: {'email': email, 'password': password}
                });
            } else {
                console.log(response.status);
                alert("incorrect information please try again");
            }
        }).catch((exception) => {
            console.log(exception);
        }); 
    };



export default function SignIn() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const history = useHistory();

    const handleSubmit = evt => {
        evt.preventDefault();
        makeRequest(email, password, history);
        //resetting content in input boxes
        setEmail("");
        setPassword("");
    };

    return (
        <div className="userValidation">
            <h1>SPRING BOOT TO DO LIST</h1>
            <div className="signup-form">
                <form onSubmit={handleSubmit}>
                    <h2>Sign in</h2>
                    <div className="form-group">
                        <input type="email" value={email} onChange={e => setEmail(e.target.value)}  className="form-control" placeholder="Email" required="required" />
                    </div>
                    <div className="form-group">
                        <input type="password" value={password} onChange={e => setPassword(e.target.value)}  className="form-control" placeholder="Password" required="required" />
                    </div>
                    <div className="form-group">
                        <button type="submit" value="submit" className="btn btn-success btn-lg btn-block">Sign in</button>
                    </div>
                </form>
                <div className="text-center">Don't have an account? <Link to="/signUp">Sign Up Here!</Link></div>
            </div>
        </div>
    );
}