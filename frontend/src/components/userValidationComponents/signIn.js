import "./userValidationStyle.css";
import { useState } from "react";
import {Link} from 'react-router-dom';
import {useHistory} from 'react-router-dom';

const useInput = initialValue => {
    const [value, setValue] = useState(initialValue);
    return {
        value,
        setValue,
        reset: () => setValue(""),
        bind: {
            value,
            onChange: event => {
                setValue(event.target.value);
            }
        }
    };
};

function makeRequest(email, password, history) {
    let data = {"email": email, "password": password};
    console.log(data);
    history.push('/toDoList');
    //get url from spring boot
    //make request to server to check if valid user sending over data json object
}



export default function SignIn() {
    const {value: password, bind: bindPassword, reset: resetPassword} = useInput("");
    const {value: email, bind: bindEmail, reset: resetEmail} = useInput("");
    const history = useHistory();

    const handleSubmit = evt => {
        evt.preventDefault();
        makeRequest(email, password, history);
        //resetting content in input boxes
        resetEmail();
        resetPassword();
    };

    return (
        <div class="userValidation">
            <div class="signup-form">
                <form onSubmit={handleSubmit}>
                    <h2>Sign in</h2>
                    <div class="form-group">
                        <input type="email" {...bindEmail}class="form-control" name="email" placeholder="Email" required="required" />
                    </div>
                    <div class="form-group">
                        <input type="password" {...bindPassword}class="form-control" name="password" placeholder="Password" required="required" />
                    </div>
                    <div class="form-group">
                        <button type="submit" value="submit" class="btn btn-success btn-lg btn-block">Sign in</button>
                    </div>
                </form>
                <div class="text-center">Don't have an account? <Link to="/signUp">Sign Up Here!</Link></div>
            </div>
        </div>
    );
}