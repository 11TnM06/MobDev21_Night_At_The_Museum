import React, { useState, useEffect } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { NavigationBar } from '..';
import { useNavigate } from 'react-router-dom';
import Axios from "axios"

function ModifyItem() {
  const [id, setId] = useState('');
  const [name, setItemName] = useState('');
  const [creatorName, setCreatorName] = useState('');
  const [description, setDescription] = useState('');
  const [time, setDateCreated] = useState('');
  const [collectionId, setCollectionId] = useState('');
  const [collection, setCollectionName] = useState('');
  const [thumbnail, setThumbnail] = useState('');
  const [file, setSelectedFile] = useState(null);

  const navigate = useNavigate();

  const [list_data, setJsonData] = useState([]);
  const [idData, setJsonIdData] = useState([]);

  useEffect(() => {
    // Make API call to fetch data
    // Replace the API_URL with your actual API endpoint
    fetch('http://localhost:3001/collections/list')
      .then(response => response.json())
      .then(list_data => {
        setJsonData(list_data.message);
        console.log(list_data.message);
      })
      .catch(error => console.log(error));
  }, []);

  const extractedNames = list_data.map((item) => item.name);

  const handleModifySubmit = (e) => {
    e.preventDefault();

    // Create the payload to send to the backend API
    const payload = new FormData();

    payload.append('file', file);
    payload.append('requests', JSON.stringify({
      id: id !== '' ? id : null,
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
    Axios.post(`http://localhost:3001/items/update/`, payload, { withCredentials: true, headers: { "Content-Type": "multipart/form-data" } })
      .then((response) => {
        return response.data
      })
      .then((data) => {
        // Handle the response from the API
        if (data.status === "success") {
          navigate('/items');
        } else {
          alert("Please upload file.")
        }

      })
      .catch((error) => {
        // Handle any errors that occurred during the API call
        console.error('API error:', error);
      });


  };

  const handleNameChange = (e) => {
    const selectedName = e.target.value;
    setCollectionName(selectedName);
    const selectedOption = list_data.find((item) => item.name === selectedName);
    setCollectionId(selectedOption.id);
  }


  useEffect(() => {
    // Make API call to fetch data
    // Replace the API_URL with your actual API endpoint
    fetch('http://localhost:3001/items/list')
      .then(response => response.json())
      .then(idData => {
        setJsonIdData(idData.message);
        setId(idData.message[0].id);
        setItemName(idData.message[0].name);
        setCreatorName(idData.message[0].creatorName);
        setDescription(idData.message[0].description);
        setDateCreated(idData.message[0].time);
        setCollectionId(idData.message[0].collectionId);
        setCollectionName(idData.message[0].collection);
        setThumbnail(idData.message[0].thumbnail);
      })
      .catch(error => console.log(error));
  }, []);
  const extractedIdNames = idData.map((item) => item.name);

  const handleIdNameChange = (e) => {
    const selectedIdName = e.target.value;
    setItemName(selectedIdName);
    const selectedOptionId = idData.find((item) => item.name === selectedIdName);

    setId(selectedOptionId.id)
    setCreatorName(selectedOptionId.creatorName ? selectedOptionId.creatorName : '');
    setDescription(selectedOptionId.description ? selectedOptionId.description : '');
    setDateCreated(selectedOptionId.time ? selectedOptionId.time : '');
    setCollectionId(selectedOptionId.collectionId ? selectedOptionId.collectionId : '');
    setCollectionName(selectedOptionId.collection ? selectedOptionId.collection : '');
    setThumbnail(selectedOptionId.thumbnail ? selectedOptionId.thumbnail : '');
  }

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);

  };

  return (
    <div>
      <NavigationBar />
      <Container className="d-flex justify-content-center">
        <div className="w-50">

          <Form onSubmit={handleModifySubmit} className="mt-3">
            <Form.Group>
              <Form.Label>Item Name:</Form.Label>
              <Form.Control as="select" onChange={handleIdNameChange}>
                {extractedIdNames.map((name, i) => (
                  <option key={i} value={name}>
                    {name}
                  </option>
                ))}
              </Form.Control>
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
                required
                type="file"
                onChange={handleFileChange}
              />

            </Form.Group>
            <Button variant="dark" type="submit" className='mt-3' onClick={handleModifySubmit}>
              Modify Item
            </Button>
          </Form>
        </div>
      </Container>
    </div>

  );
};

export { ModifyItem };
