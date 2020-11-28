import "./userValidationStyle.css";
import { useState } from "react";
import { Link } from 'react-router-dom';
import { useHistory } from 'react-router-dom';

export default function SignUp() {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password1, setPassword1] = useState("");
    const [password2, setPassword2] = useState("");
    const history = useHistory();

    const handleSubmit = evt => {
        evt.preventDefault();
        if (password1 !== password2) {
            alert(`Error: passwords must be equal`);
            return;
        }
        else {
            makeRequest();
        }
    };

    const makeRequest = () => {
        document.body.style.cursor = 'wait';
        let data = { first_name: firstName, last_name: lastName, email: email, user_password: password1 };
        console.log(data);

        let url = 'http://localhost:8080/user/add';
        fetch(url, {
            method: 'POST',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(data)
        }).then(response => response.json())
            .then(data => {
                console.log(data);
                document.body.style.cursor = 'default';
                history.push('/signIn');
            }).catch((exception) => {
                console.log(exception);
            });
    }

    return (
        <div className="userValidation">
            <h1>SPRING BOOT TO DO LIST</h1>
            <div className="form">
                <form onSubmit={handleSubmit}>
                    <h2>Register</h2>
                    <div className="form-group">
                        <input type="text" value={firstName} onChange={e => setFirstName(e.target.value)} className="form-control" placeholder="First Name" required="required" />
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
                        <button type="submit" value="submit">Register Now</button>
                    </div>
                </form>
                <div className="text-center">Already have an account? <Link to="/signIn">Sign In Here!</Link></div>
            </div>
        </div>
    );
}