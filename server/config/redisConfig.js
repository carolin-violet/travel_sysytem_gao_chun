var redis = require('redis');

const client = redis.createClient({
  host: '127.0.0.1',
  port: 6379
});

client.on('error', (err) => {
  console.log('redis error>>>', err);
});

client.connect()

function writeToRedis(key, str, time) {
  client.setEx(key, time, str)
}

function readFromRedis(key) {
  return client.get(key)
}


module.exports = { writeToRedis, readFromRedis }