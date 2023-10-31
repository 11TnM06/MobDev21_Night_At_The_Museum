import React from 'react'
import { useEffect, useState } from "react";
import { NavigationBar, ButtonEvent, StoryTable } from "../../components";

function Stories () {
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
            <ButtonEvent param={'story'}/>
            <StoryTable />
        </div>
    );
}

export { Stories }