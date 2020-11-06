import "./userValidationStyle.css";
import { useState } from "react";
import {Link} from 'react-router-dom';
import {useHistory} from 'react-router-dom';


function makeRequest(firstName, lastName, email, password, history) {
    let data = {"firstName": firstName, "lastName": lastName, "email": email, "password": password};
    console.log(data);
    history.push('/signIn');
    //get url from spring boot
    //make request to server to check if allowed to be added to db and then posted to db

}

export default function SignUp() {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password1, setPassword1] = useState("");
    const [password2, setPassword2] = useState("");
    const history = useHistory();

    const handleSubmit = evt => {
        evt.preventDefault();
        if(password1 !== password2){
            alert(`Error: passwords must be equal`);
            return;
        }
        else {
            makeRequest(firstName, lastName, email, password1, history);
        }
        //resetting content in input boxes
        setFirstName("");
        setLastName("");
        setEmail("");
        setPassword1("");
        setPassword2("");
    };


    return (
        <div className="userValidation">
            <h1>SPRING BOOT TO DO LIST</h1>
            <div className="signup-form">
                <form onSubmit={handleSubmit}>
                    <h2>Register</h2>
                    <div className="form-group">
                            <input type="text" value={firstName} onChange={e => setFirstName(e.target.value)} className="form-control"  placeholder="First Name" required="required" />
                    </div>
                    <div className="form-group">
                        <input type="text" value={lastName} onChange={e => setLastName(e.target.value)} className="form-control" placeholder="Last Name" required="required" />
                    </div>
                    <div className="form-group">
                        <input type="email" value={email} onChange={e => setEmail(e.target.value)} className="form-control" placeholder="Email" required="required" />
                    </div> 
                    <div className="form-group">
                        <input type="password" value={password1} onChange={e => setPassword1(e.target.value)} className="form-control" name="password" placeholder="Password" required="required" />
                    </div>
                    <div className="form-group">
                        <input type="password" value={password2} onChange={e => setPassword2(e.target.value)} className="form-control" placeholder="Confirm Password" required="required" />
                    </div>
                    <div className="form-group">
                        <button type="submit" value="submit" className="btn btn-success btn-lg btn-block">Register Now</button>
                    </div>
                </form>
                <div className="text-center">Already have an account? <Link to="/signIn">Sign In Here!</Link></div>
            </div>
        </div>
    );
}