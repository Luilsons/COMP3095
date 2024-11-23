print('START');

// Access or create the 'product-service' database
db = db.getSiblingDB('product-service');

// Create a user with 'readWrite' role on the 'product-service' database
db.createUser({
    user: 'admin',
    pwd: 'password',
    roles: [{ role: 'readWrite', db: 'product-service' }]
});

// Create the 'user' collection (modify if necessary to match your use case)
db.createCollection('user');

print('END');
