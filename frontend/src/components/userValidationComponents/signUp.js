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

function makeRequest(firstName, lastName, email, password, history) {
    let data = {"firstName": firstName, "lastName": lastName, "email": email, "password": password};
    console.log(data);
    history.push('/signIn');
    //get url from spring boot
    //make request to server to check if allowed to be added to db and then posted to db

}

export default function SignUp() {
    const {value: firstName, bind: bindFirstName, reset: resetfirstName} = useInput("");
    const {value: lastName, bind: bindLastName, reset: restLastName} = useInput("");
    const {value: email, bind: bindEmail, reset: resetEmail} = useInput("");
    const {value: password1, bind: bindPassword1, reset: resetPassword1} = useInput("");
    const {value: password2, bind: bindPassword2, reset: resetPassword2} = useInput("");
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
        resetfirstName();
        restLastName();
        resetEmail();
        resetPassword1();
        resetPassword2();
    };


    return (
        <div class="userValidation">
            <div class="signup-form">
                <form onSubmit={handleSubmit}>
                    <h2>Register</h2>
                    <div class="form-group">
                            <input type="text" {...bindFirstName} class="form-control" name="first_name" placeholder="First Name" required="required" />
                    </div>
                    <div class="form-group">
                        <input type="text" {...bindLastName} class="form-control" name="last_name" placeholder="Last Name" required="required" />
                    </div>
                    <div class="form-group">
                        <input type="email" {...bindEmail} class="form-control" name="email" placeholder="Email" required="required" />
                    </div>
                    <div class="form-group">
                        <input type="password" {...bindPassword1}  class="form-control" name="password" placeholder="Password" required="required" />
                    </div>
                    <div class="form-group">
                        <input type="password" {...bindPassword2} class="form-control" name="confirm_password" placeholder="Confirm Password" required="required" />
                    </div>
                    <div class="form-group">
                        <button type="submit" value="submit" class="btn btn-success btn-lg btn-block">Register Now</button>
                    </div>
                </form>
                <div class="text-center">Already have an account? <Link to="/signIn">Sign In Here!</Link></div>
            </div>
        </div>
    );
}