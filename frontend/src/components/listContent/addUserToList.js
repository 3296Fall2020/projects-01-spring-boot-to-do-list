import React, { useContext, useState } from 'react';
import { Context } from '../context';
import './listContent.css'

export default function AddListUser({ show, close }) {
    const {list, fetchListUsers } = useContext(Context);
    const [email, setEmail] = useState("");

    const getUser = evt => {
        evt.preventDefault();
        document.body.style.cursor='wait';
        console.log(email);
        fetch('http://springboottodolist-env-1.eba-dmpcuc7f.us-east-2.elasticbeanstalk.com/user/getUserEmail?email=' + email)
            .then(res => res.json())
            .then(data => {
                console.log(data);
                addUserToList(data.id);
                setEmail("")
            }).catch((exception) => {
                document.body.style.cursor='default';
                console.log(exception);
                alert("invalid user, please try again");
                setEmail("")
                close(false);
            });
    };

    const addUserToList = (id) => {
        console.log("list id: " + list.id);
        console.log("user id: " + id);
        let url = 'http://springboottodolist-env-1.eba-dmpcuc7f.us-east-2.elasticbeanstalk.com/list/addUserToList?user_id=' + id + '&list_id=' + list.id;
        fetch(url, {
            method: 'POST',
            headers: new Headers({
                'Content-Type': 'application/json'
            })
        }).then((response) => {
            if(response.status === 404){
                alert("invalid user, please try again");
            }
            console.log(response);
            fetchListUsers(list);
            document.body.style.cursor='default';
            close(false);
        })
            .catch((exception) => {
                document.body.style.cursor='default';
                console.log(exception);
                alert("user could not be added to list, please try again");
            });
    }


    return (
        <div className="add_user_list_popup" style={{ display: show ? "block" : "none" }}>
            <form onSubmit={getUser}>
                <h3>Enter email of member you want to add</h3>
                <button className="add_user_close" type="button" onClick={() => { close(false) }}>&times;</button>
                <div className="form-group">
                    <input type="email" value={email} onChange={e => setEmail(e.target.value)} className="form-control" placeholder="Email" required="required" />
                </div>
                <button className="add_user_submit" type="submit" >Add User</button>
            </form>
        </div>
    )
}
