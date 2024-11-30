# EPL 231 Project 

## Trie using RobinHood Hashing
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/AntoniosKalattasV2.drawio_page-0001.jpg)
***

### Dictionary Source
University of Michigan's English Word List
https://websites.umich.edu/~jlawler/wordlist.html?utm_source=chatgpt.com
***

# Random Word Generator 
![alt text](https://github.com/AntoniosKalattas/epl231/blob/main/img/Histogram_%20length%20of%20each%20word-2.png)

***

# Memory Usage

### Trie Using RobinHood hashing
**Instance of Element**: 8 bytes (2x4 ints) + 12 byte header = 20 bytes. But becuase Java objects are aligned to an 8-byte boundary = **24 Bytes**

**Instance of TrieNode**: (5x4 int) + (2x4 byte references) + 12 byte header = **40 bytes**

**Total Memory Usage** = N x [(Instance of Element + Instance of TrieNode) + (12 bytes + (arraySize x 4 bytes)]  = N x [(40 bytes + 24 bytes) +(12 bytes + (size x 4 bytes) ]
> N: number ob trie nodes.


