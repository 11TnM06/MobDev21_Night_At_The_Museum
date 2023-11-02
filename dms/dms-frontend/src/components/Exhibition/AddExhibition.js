import React, { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { NavigationBar } from '..';
import { useNavigate } from 'react-router-dom';
const AddExhibition = () => {
  const [name, setExihibitionName] = useState('');
  const [museumName, setMuseumName] = useState('');
  const [place, setPlace] = useState('');
  const [thumbnail, setThumbnail] = useState('');
  const navigate = useNavigate();
  const [data, setJsonData] = useState([]);
  const handleSubmit = (e) => {
    e.preventDefault();
    
    // Create the payload to send to the backend API
    const payload = {
      name: name !== '' ? name : null,
      museumName: museumName !== '' ? museumName : null,
      place: place !== '' ? place : null,
      thumbnail: thumbnail !== '' ? thumbnail : null,
    };
    const fetchData = () => {
      // Fetch data from the backend API to populate the table
      // Replace the URL with your actual API endpoint
      fetch('http://localhost:3001/exhibitions/list')
        .then(response => response.json())
        .then(data => { 
              setJsonData(data.message);
          })
        .catch(error => console.log(error));
    };
    // Send the payload to the backend API
    // Replace the URL with your actual API endpoint
    fetch('http://localhost:3001/exhibitions/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    })
      .then((response) => response.json())
      .then((data) => {
        fetchData();
      })
      .catch((error) => {
        // Handle any errors that occurred during the API call
        console.error('API error:', error);
      });
      navigate('/exhibitions');
  };


  return (
    <div>
        <NavigationBar />
    <Container className="d-flex justify-content-center">
      <div className="w-50">
        <h1></h1>
        <Form >
            <Form.Group controlId="name">
                <Form.Label>Exhibition Name:</Form.Label>
                <Form.Control 
                    type="text"
                    value={name}
                    onChange={(e) => setExihibitionName(e.target.value)}
                />
            </Form.Group>
            <Form.Group controlId="museumName">
                <Form.Label>Museum Name:</Form.Label>
                <Form.Control
                    type="text"
                    value={museumName}
                    onChange={(e) => setMuseumName(e.target.value)}
                />
            </Form.Group>
            
            <Form.Group controlId="place">
                <Form.Label>Place:</Form.Label>
                <Form.Control
                    type="text"
                    value={place}
                    onChange={(e) => setPlace(e.target.value)}
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
            
            <Button variant="dark" type="submit" className='mt-3' onClick={handleSubmit}>
                Add Exhibition
            </Button>

        </Form>
      </div>
    </Container>
    </div>
    
  );
};

export { AddExhibition } ;
