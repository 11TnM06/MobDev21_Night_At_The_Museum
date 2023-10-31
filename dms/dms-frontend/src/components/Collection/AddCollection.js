import React, { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { NavigationBar } from '..';
import { useNavigate } from 'react-router-dom';

const AddCollection = () => {

  const [name, setCollectionName] = useState('');
  const [description, setDescription] = useState('');
  const [place, setPlace] = useState('');
  const [icon, setIcon] = useState('');
  const [thumbnail, setThumbnail] = useState('');
  const navigate = useNavigate();
  const handleAddSubmit = (e) => {
    e.preventDefault();
    
    // Create the payload to send to the backend API
    const payload = {
      name: name !== '' ? name : null,
      description: description !== '' ? description : null,
      place: place !== '' ? place : null,
      icon: icon !== '' ? icon : null,
      thumbnail: thumbnail !== '' ? thumbnail : null,
    };

    // Send the payload to the backend API
    // Replace the URL with your actual API endpoint
    fetch('http://localhost:3001/collections/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    })
      .then((response) => response.json())
      .then((data) => {
        // Handle the response from the API
        console.log('API response:', data);
      })
      .catch((error) => {
        // Handle any errors that occurred during the API call
        console.error('API error:', error);
      });
      navigate('/collections');
  };

  

  return (
    <div>
        <NavigationBar />
        <Container className="d-flex justify-content-center">
          <div className="w-50">
            <h1></h1>
            <Form>
                <Form.Group controlId="name">
                    <Form.Label>Collection Name:</Form.Label>
                    <Form.Control 
                        type="text"
                        value={name}
                        onChange={(e) => setCollectionName(e.target.value)}
                    />
                </Form.Group>

                <Form.Group controlId="description">
                    <Form.Label>Description:</Form.Label>
                    <Form.Control
                        type="text"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                </Form.Group>

                <Form.Group controlId="Place">
                    <Form.Label>Place:</Form.Label>
                    <Form.Control
                        type="text"
                        value={place}
                        onChange={(e) => setPlace(e.target.value)}
                    />
                </Form.Group>

                <Form.Group controlId="icon">
                <Form.Label>Icon:</Form.Label>
                <Form.Control
                  type="text"
                  value={icon}
                  onChange={(e) => setIcon(e.target.value)}
                />
                </Form.Group>

                <Form.Group controlId="thumbnail">
                <Form.Label>Thumbnail:</Form.Label>
                <Form.Control
                  type="text"
                  value={thumbnail}
                  onChange={(e) => setThumbnail(e.target.value)}
                />
                </Form.Group>
                <Button variant="dark" type="submit" className='mt-3' onClick={handleAddSubmit}>
                    Add Collection
                </Button>


            </Form>
          </div>
        </Container>
    </div>
    
  );
};

export { AddCollection } ;
