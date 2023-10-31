import React, { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { NavigationBar } from '..';
const DelItem = () => {
  const [collectionId, setCollectionId] = useState('');


  const handleSubmit = (e) => {
    e.preventDefault();

    // Create the payload to send to the backend API
    const payload = {
        collectionId,
    };

    // Send the payload to the backend API
    // Replace the URL with your actual API endpoint
    fetch('http://localhost:3001/items/delete', {
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
  };

  return (
    <div>
        <NavigationBar />
        <Container className="d-flex justify-content-center">
          <div className="w-50">
            <h1></h1>
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="collectionId">
                    <Form.Label>Item ID:</Form.Label>
                    <Form.Control 
                        type="text"
                        value={collectionId}
                        onChange={(e) => setCollectionId(e.target.value)}
                    />
                </Form.Group>

                
                <Button variant="primary" type="submit" className='mt-3'>
                    Delete Item
                </Button>
            </Form>
          </div>
        </Container>
    </div>
    
  );
};

export { DelItem } ;
