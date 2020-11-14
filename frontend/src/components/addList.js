
import {Modal, Button} from 'react-bootstrap';
import React, { useState } from 'react';

export default function AddList() {
    const [showModal, setShowModal] = useState(false);
    const [listName, setListName] = useState("");


    const addList = evt => {
      console.log(listName)
      //makeRequest(email, password, history);
      //resetting content in input boxes
  };

    return (
        <div id="addList">
           <Modal show={showModal} onHide={() => setShowModal(false)}>
            <Modal.Header closeButton>
              <Modal.Title>Add New List</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <input type="name" value={listName} onChange={e => setListName(e.target.value)} className="form-control" placeholder="Name" required="required" />
            </Modal.Body>
            <Modal.Footer>
              <Button variant="primary" onClick={() => addList()}>Add</Button>
            </Modal.Footer>
          </Modal>
        </div>
    )
}