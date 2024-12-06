# EPL 231 Project 

## Trie using RobinHood Hashing
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/Untitled%20Diagram.jpg)
***

### Dictionary Source
University of Michigan's English Word List
https://websites.umich.edu/~jlawler/wordlist.html?utm_source=chatgpt.com
***

# Random Word Generator 
Our generator produces lengths that follow the **shifted Poisson** distribution.
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/Histogram_%20length%20of%20each%20word-2.png)

***
# Memory Usage

### Trie Using RobinHood hashing
**Instance of Element**: 8 bytes (2x4 ints) + 12 byte header = 20 bytes. But becuase Java objects are aligned to an 8-byte boundary = **24 Bytes**

**Instance of TrieNode**: (5x4 int) + (2x4 byte references) + 12 byte header = **40 bytes**

**Total Memory Usage** = N x [(Instance of Element + Instance of TrieNode) + (12 bytes + (arraySize x 4 bytes)]  + nullPointers x 4 bytes = N x [(40 bytes + 24 bytes) +(12 bytes + (size x 4 bytes) ] + nullPointers x 4 bytes 
> N: number ob trie nodes.


### Trie Static (Simple)
**Instance of TrieNode:** 12 bytes (3x4 byes int) + 4 bytes (children reference)  + 12 byte (header) = 28 bytes = 28 bytes.

***

#### Dyanmic Trie Implementation
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/DynamicTrieV2.png)


#### Static Trie Implementation
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/StaticTrieV2.png)


#### Static Vs Dynamic for random length words
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/StaticVsDynamicRandom.png)

#### Static Vs Dynamic lenght 4
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/StaticVsDynamicLength4.png)

### Static Vs Dynamic length 8
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/StaticVsDynamicLength8.png)

### Static Vs Dynamic length 12
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/StaticVsDynamicLength12.png)

#### Static Vs Dynamic Length 20
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/StaticVsDynamicLength20.png)

#### Static Vs Dynamic Length 20
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/StaticVsDynamicLength20.png)

#### Static Vs Dynamic Length 8, input>700K
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/StaticVsDynamicLength8Big.png)







