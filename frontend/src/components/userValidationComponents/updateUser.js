import "./userValidationStyle.css";
import { useState, useEffect } from "react";
import { useHistory, useLocation } from 'react-router-dom';


export default function UpdateUser() {
    const [user, setUser] = useState({});
    const [password2, setPassword2] = useState("");
    const [deleteMessage, setDeleteMessage] = useState(false);
    const location = useLocation();
    const history = useHistory();

    useEffect(() => {
        setUser(location.state.user);
        setPassword2(location.state.user.user_password);
        console.log(user);
    }, [location]);


    const handleSubmit = (e) => {
        e.preventDefault();
        if (password2 !== user.user_password) {
            alert(`Error passwords must be the same`);
            return;
        } else if (password2.trim() === "" || user.first_name.trim() === "" || user.last_name.trim() === "" || user.user_password.trim() === "") {
            alert('Error all input fields must be filled out');
            return;
        }
        else {
            console.log("updating user");
            console.log(user);
            makeRequest(user);
        }
    };

    const makeRequest = (user) => {
        document.body.style.cursor = 'wait';
        let url = 'http://springboottodolist-env-1.eba-dmpcuc7f.us-east-2.elasticbeanstalk.com/user/update/' + user.id;
        fetch(url, {
            method: 'PUT',
            headers: new Headers({
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(user)
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                document.body.style.cursor = 'default';
                alert("user has been updated");
                history.push({
                    pathname: '/signIn'
                })
            })
            .catch((exception) => {
                document.body.style.cursor='default';
                console.log(exception);
                alert("user could not be updated, please try again");
            });
    }

    const handleDelete = (e) => {
        e.preventDefault();
        console.log("deleting user");
        console.log(user);
        document.body.style.cursor = 'wait';
        let url = 'http://springboottodolist-env-1.eba-dmpcuc7f.us-east-2.elasticbeanstalk.com/user/delete/' + user.id;
        fetch(url, {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).then((response) => {
            setDeleteMessage(false);
            document.body.style.cursor = 'default';
            alert("user has been deleted");
            history.push({ pathname: '/' });
        })
            .catch((exception) => {
                document.body.style.cursor='default';
                console.log(exception);
                alert("user could not be deleted, please try again");

            });
    }

    const handleDeleteMessage = (e) => {
        e.preventDefault();
        setDeleteMessage(!deleteMessage);
    }

    const goBack = () => {
        history.push({
            pathname: '/toDoList',
            state: {'user': user}
        });
    }


    return (
        <div className="userValidation">
            <h1>SPRING BOOT TO DO LIST</h1>
            <button className="back_button" onClick={goBack}>Back</button>
            <div className="form">
                <form>
                    <h2>Update Your Information</h2>
                    <div className="form-group">
                        <span className="input_header">Change First Name:</span>
                        <input type="text" value={user.first_name || ""} onChange={e => setUser({ ...user, first_name: e.target.value })} className="form-control" placeholder="First Name" required="required" />
                    </div>
                    <div className="form-group">
                        <span className="input_header">Change Last Name:</span>
                        <input type="text" value={user.last_name || ""} onChange={e => setUser({ ...user, last_name: e.target.value })} className="form-control" placeholder="Last Name" required="required" />
                    </div>
                    <div className="form-group">
                        <span className="input_header">Change Password:</span>
                        <input type="password" value={user.user_password || ""} onChange={e => setUser({ ...user, user_password: e.target.value })} className="form-control" name="password" placeholder="Password" required="required" />
                    </div>
                    <div className="form-group">
                        <span className="input_header">Confirm New Password:</span>
                        <input type="password" value={password2 || ""} onChange={e => setPassword2(e.target.value)} className="form-control" placeholder="Confirm Password" required="required" />
                    </div>
                    <div className="form-group">
                        <button type="submit" onClick={handleSubmit}>Update User</button>
                    </div>
                    <div className="form-group">
                        <button onClick={handleDeleteMessage}>Delete User</button>
                    </div>
                    <div className="delete_confirmation" style={{ display: deleteMessage ? "block" : "none" }}>
                        <span className="delete_message">Are you sure you want to delete this user?</span>
                        <button className="deleteYes" onClick={handleDelete}>Yes</button>
                        <button className="deleteNo" onClick={handleDeleteMessage}>No</button>
                    </div>
                </form>
            </div>
        </div>
    );
}