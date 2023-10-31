import React from 'react';
import { Button, Container } from 'react-bootstrap';
import { useNavigate } from "react-router-dom";

const ButtonEvent = ({ param }) => {

    const containerStyles = {
        marginTop: '30px',
        display: 'flex',
        justifyContent: 'flex-end',
        paddingRight: '0px',
        marginRight: '30px'
    };

    const buttonStyles = {
        fontFamily: 'Arial, sans-serif',
        fontWeight: 'bold',
        marginRight: '10px',
    };

    const navigate = useNavigate();
    const handleAddClick = () => {
        if (param === 'item') {
            navigate('/items/add');
        }
        else if (param === 'collection') {
            navigate('/collections/add');
        }
        else if (param === 'exhibition') {
            navigate('/exhibitions/add');
        }
        else if (param === 'story') {
            navigate('/stories/add');
        }
    };


    const handleModifyClick = () => {
        if (param === 'item') {
            navigate('/items/modify');
        }
        else if (param === 'collection') {
            navigate('/collections/modify');
        }
        else if (param === 'exhibition') {
            navigate('/exhibitions/modify');
        }
        else if (param === 'story') {
            navigate('/stories/modify');
        }
    };

    return (
        <Container style={containerStyles}>
        <Button onClick={handleAddClick} variant="dark" style={buttonStyles}>Add</Button>
        <Button onClick={handleModifyClick} variant="dark" style={buttonStyles}>Modify</Button>
        
        
        </Container>
    );
};

export { ButtonEvent };