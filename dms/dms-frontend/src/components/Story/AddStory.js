import React, { useState, useEffect } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { NavigationBar } from '..';
import { useNavigate } from 'react-router-dom';
const AddStory = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [collectionId, setCollectionId] = useState('');
  const [thumbnail, setThumbnail] = useState('');

  const [childForms, setChildForms] = useState([]);


  const navigate = useNavigate();

  const handleAddSubmit = (e) => {
    e.preventDefault();

    // Create the payload to send to the backend API
    const payload = {
      title: title !== '' ? title : null,
      collectionId: collectionId !== '' ? collectionId : null,
      description: description !== '' ? description : null,
      thumbnail: thumbnail !== '' ? thumbnail : null,
      pages: childForms.map((page) => ({
        thumbnail: page.thumbnail !== '' ? page.thumbnail : null,
        description: page.description !== '' ? page.description : null,
      }))
    };
    // Send the payload to the backend API
    // Replace the URL with your actual API endpoint
    fetch('http://localhost:3001/stories/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.status === 'success') {
          navigate('/stories');
        } else {
          alert('An error occurred. Unable to add story.');
        }
      })
      .catch((error) => {
        // Handle any errors that occurred during the API call
        console.error('API error:', error);
      });
  };


  const [list_data, setJsonData] = useState([]);
  const extractedNames = list_data.map((item) => item.name);
  const [collectionName, setCollectionName] = useState('');

  useEffect(() => {
    // Make API call to fetch data
    // Replace the API_URL with your actual API endpoint
    fetch('http://localhost:3001/collections/list')
      .then(response => response.json())
      .then(list_data => {
        setJsonData(list_data.message);
      })
      .catch(error => console.log(error));
  }, []);

  const handleNameChange = (e) => {
    const selectedName = e.target.value;
    setCollectionName(selectedName);
    const selectedOption = list_data.find((item) => item.name === selectedName);
    setCollectionId(selectedOption.id);
  }

  const handleAddPageButtonClick = () => {
    const newPage = {
      thumbnail: '',
      description: ''
    };

    setChildForms([...childForms, newPage]);
  };
  const handlePageInputChange = (index, field, value) => {
    const updatedChildForms = [...childForms];
    updatedChildForms[index][field] = value;
    setChildForms(updatedChildForms);
  };
  return (
    <div>
      <NavigationBar />
      <Container className="d-flex justify-content-center">
        <div className="w-50">
          <h1></h1>
          <Form>

            <Form.Group controlId="title">
              <Form.Label>Story Title:</Form.Label>
              <Form.Control
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              />
            </Form.Group>

            <Form.Group>
              <Form.Label>Collection Name:</Form.Label>
              <Form.Control as="select" onChange={handleNameChange}>
                {extractedNames.map((name) => (
                  <option key={name} value={name}>
                    {name}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>

            <Form.Group controlId="description">
              <Form.Label>Description:</Form.Label>
              <Form.Control
                type="text"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
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

            {childForms.map((page, index) => (
              <Form key={index}>
                <Form.Group controlId={`pageThumbnail-${index}`}>
                  <Form.Label>Page Thumbnail</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Enter Thumbnail"
                    value={page.thumbnail}
                    onChange={(e) =>
                      handlePageInputChange(index, 'thumbnail', e.target.value)
                    }
                  />
                </Form.Group>
                <Form.Group controlId={`pageDescription-${index}`}>
                  <Form.Label>Page Description</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Enter Description"
                    value={page.description}
                    onChange={(e) =>
                      handlePageInputChange(index, 'description', e.target.value)
                    }
                  />
                </Form.Group>
                <hr />
              </Form>
            ))}

            <Button variant="dark" type="button" className='mt-3' onClick={handleAddPageButtonClick}>
              Add Page
            </Button>

            <Button variant="dark" type="submit" className='mt-3 ms-3' onClick={handleAddSubmit}>
              Add Story
            </Button>

          </Form>
        </div>
      </Container>
    </div>

  );
};

export { AddStory };
