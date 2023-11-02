import React, { useState, useEffect } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { NavigationBar } from '../../components';
import Axios from "axios"
import { useNavigate } from 'react-router-dom';
const AddItem = () => {
  const [name, setItemName] = useState('');
  const [creatorName, setCreatorName] = useState('');
  const [description, setDescription] = useState('');
  const [time, setDateCreated] = useState('');
  const [collectionId, setCollectionId] = useState('');
  const [collection, setCollectionName] = useState('');
  const [thumbnail, setThumbnail] = useState('');
  const [file, setSelectedFile] = useState(null);

  const navigate = useNavigate();
  const [selectedNameId, setSelectedNameId] = useState('');
  const [list_data, setJsonData] = useState([]);

  useEffect(() => {
    // Make API call to fetch data
    // Replace the API_URL with your actual API endpoint
    fetch('http://localhost:3001/collections/list')
      .then(response => response.json())
      .then(list_data => {
        setJsonData(list_data.message);
        setCollectionName(list_data.message[0].name);
        setCollectionId(list_data.message[0].id);
      })
      .catch(error => console.log(error));
  }, []);

  const extractedNames = list_data.map((item) => item.name);

  const handleSubmit = (e) => {
    e.preventDefault();

    // Create the payload to send to the backend API
    const payload = new FormData();

    payload.append('file', file);
    payload.append('requests', JSON.stringify({
      name: name !== '' ? name : null,
      creatorName: creatorName !== '' ? creatorName : null,
      description: description !== '' ? description : null,
      time: time !== '' ? time : null,
      collectionId: collectionId !== '' ? collectionId : null,
      collection: collection !== '' ? collection : null,
      thumbnail: thumbnail !== '' ? thumbnail : null
    }));

    // Send the payload to the backend API
    // Replace the URL with your actual API endpoint
    Axios.post(`http://localhost:3001/items/add/`, payload, { withCredentials: true, headers: { "Content-Type": "multipart/form-data" } })
      .then((response) => response.data)
      .then((data) => {
        // Handle the response from the API
        if (data.status === "success") {
          navigate('/items');
        } else {
          alert("Please upload file.");
          
        }
      })
      .catch((error) => {
        // Handle any errors that occurred during the API call
        console.error('API error:', error);
      });


  };

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);

  };

  const handleNameChange = (e) => {
    const selectedName = e.target.value;
    setCollectionName(selectedName);
    const selectedOption = list_data.find((item) => item.name === selectedName);
    setCollectionId(selectedOption.id);
  }


  return (
    <div>
      <NavigationBar />
      <Container className="d-flex justify-content-center">
        <div className="w-50">

          <Form onSubmit={handleSubmit} className="mt-3">
            <Form.Group controlId="name">
              <Form.Label>Item Name:</Form.Label>
              <Form.Control
                type="text"
                value={name}
                onChange={(e) => setItemName(e.target.value)}
              />
            </Form.Group>

            <Form.Group controlId="creatorName">
              <Form.Label>Creator Name:</Form.Label>
              <Form.Control
                type="text"
                value={creatorName}
                onChange={(e) => setCreatorName(e.target.value)}
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

            <Form.Group>
              <Form.Label>Collection Name:</Form.Label>
              <Form.Control as="select" onChange={handleNameChange}>
                {extractedNames.map((name, i) => (
                  <option key={i} value={name}>
                    {name}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>

            <Form.Group controlId="time">
              <Form.Label>Date Created:</Form.Label>
              <Form.Control
                type="text"
                value={time}
                onChange={(e) => setDateCreated(e.target.value)}
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

            <Form.Group controlId="file">
              <Form.Label>File:</Form.Label>
              <Form.Control
                type="file"
                onChange={handleFileChange}
              />
            </Form.Group>

            <Button variant="dark" type="submit" className='mt-3' onClick={handleSubmit}>
              Add Item
            </Button>
          </Form>
        </div>
      </Container>
    </div>

  );
};

export { AddItem };
