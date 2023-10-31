import React from 'react'
import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { NavigationBar, ButtonEvent, ItemTable, CollectionTable } from "../../components";
import { Container } from "react-bootstrap";

function Collections () {
  const [authenticated, setauthenticated] = useState(null);
    useEffect(() => {
    const loggedInUser = localStorage.getItem("authenticated");
    if (loggedInUser) {
        setauthenticated(loggedInUser);
    }
}, []);

    return (
        <div >
            <NavigationBar />
            <ButtonEvent param='collection'/>
            <CollectionTable />
        </div>
    );
}

export { Collections }