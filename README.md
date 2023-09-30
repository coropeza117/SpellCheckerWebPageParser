# SpellCheckerWebPageParser

-Project Overview

In this project, the primary goal was to implement and utilize several array algorithms, most notably mergesort, to create a web page spell checker. The web page spell checker is capable of downloading web pages, extracting text content, and then verifying the spelling of words against a predefined dictionary.

• Array Algorithms Implemented

The core of this project is the implementation of various array algorithms within the SortUtils class. These algorithms include:

    Mergesort: This classic sorting algorithm, as described in the textbook, was implemented using helper methods for merging sorted segments of an array. Two variants of mergesort were developed: mergeSortKeep and mergeSortMove.

    Set Difference (A - B): A method was implemented to remove all elements from one sorted array segment that also exist in another sorted array segment.

    Remove Duplicates: Another algorithm was implemented to remove duplicate elements from a sorted array segment while preserving the sorted order.

These array algorithms, along with mergesort, were specifically designed to operate without the need for extensive memory allocation, ensuring efficiency in processing large data sets.

• Web Spell Checker

The SpellCheck class utilizes the array algorithms from SortUtils to implement a spell-checking utility for web pages. It provides the following functionality:

    Parsing Web Pages: The program downloads web pages from specified URLs and extracts text content for further processing.

    Spell Checking: The extracted text content is tokenized into words, and each word is checked against a dictionary of valid words to identify misspelled words.

    Word Tokenization: To break the text into words, the program uses regular expressions that consider common word delimiters, such as spaces, punctuation, and hyphens.

    Element Exclusion: The program intelligently skips certain HTML elements, such as those within <script> and <style> tags, to avoid spell-checking code and non-visible content.

• Accomplishments

Since its completion, the "SpellCheckerWebPageParser" project has successfully demonstrated the following accomplishments:

    Robust Web Page Parsing: The project can download web pages, parse their content, and extract text effectively, enabling the spell checker to operate on real-world web content.

    Efficient Sorting Algorithms: The implemented sorting algorithms, particularly mergesort variants, have proven efficient in sorting large arrays with minimal memory overhead.

    Spell Checking Accuracy: The spell checker reliably identifies misspelled words within web page content by referencing a dictionary of valid words.

    Educational Value: The project serves as an educational resource for students learning about data structures, algorithms, and their practical applications in web content processing.
