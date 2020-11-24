import React, { useContext, useState, useEffect } from 'react';
import { Context } from '../context'
import Item from './item'
import './listItems.css'


export default function ListItems() {
    const [user, lists, list, listUsers, setList, setListUsers, filterResults, filterLists, fetchLists, fetchListUsers, fetchList] = useContext(Context);
    const [listItems, setListItems] = useState([]);

    useEffect(() => {
        fetchListIems();
    }, [list, listUsers]);

    const fetchListIems = () => {
        fetch('http://localhost:8080/item/getListItems/' + list.id)
            .then(res => res.json())
            .then(data => {
                console.log(data);
                setListItems(data);
            }).catch((exception) => {
                console.log(exception);
            })

    }

    const handleClick = (item) => {
        console.log(item);
    }


    if (listItems.length > 0) {
        return (
            <div className="items_list_container">
                <div className="to_do_container">
                <h2 className="column_name">TO DO</h2>
                {listItems.filter(function (item) {
                    return item.completion.id === 1;
                }).map(function (item) {
                    return (
                        <Item key={item.id} item={item} click={handleClick}/>
                    )
                })}
                </div>
                <div className="in_progress_container">
                <h2 className="column_name">IN PROGRESS</h2>
                {listItems.filter(function (item) {
                    return item.completion.id === 2;
                }).map(function (item) {
                    return (
                        <Item key={item.id} item={item} click={handleClick}/>
                    )
                })}
                </div>
                <div className="to_do_container">
                <h2 className="column_name">DONE</h2>
                {listItems.filter(function (item) {
                    return item.completion.id === 3;
                }).map(function (item) {
                    return (
                        <Item key={item.id} item={item} click={handleClick} />
                    )
                })}
                </div>
            </div>
        )
    } else {
        return (
            <div className="empty_items_list_container">
                <span>¯\_(ツ)_/¯</span>
                <p>No items could be found</p>
            </div>
        );
    }
}
