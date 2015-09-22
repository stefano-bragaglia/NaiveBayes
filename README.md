# NaiveBayes
Java Framework for Naive Bayes classifiers

    classify( f_1, ..., f_n ) = argmax_c p(C = c) 
    
- **F_1, ..., F_n:** list of features 
- **f_1, ..., f_n:** values of the features

If we use a simple *Bag of Words model*, the features are the words occurring in a document and the values their number of occurrences.

- **C:** the document category
- **c:** one of the possible categories

- **argmax_c:** the "argmax function" which returns the category **c** with the highest value for the following distribution

- **p(C = c):** the probability rhat a given document belongs to category **c**

- **p(F_i = f_i | C = c):** the probability that the feature **F_i** occurs in a sample of category **c**


But how do we compute the lastly mentioned probability? Assume for now we already trained 
the classifier though we yet don’t know exactly how. Let’s assume the classifier knows 
how many times he has seen the word **F_i** in a document of class **c** and call this 
value **f_i,c** and that he knows how many times **f_i,total**￼ he has seen the word 
at all. We can compute this probability now as following:

    p(F_i = f_i | C = c) = f_i,c / f_i,total

We have not yet taken into account the actual number **f_i**￼of the occurences. The afore 
mentioned equation is suitable if we process the document word by word, but we assume we 
are getting the number of occurrences for each word as input so we just put **f_i** as an 
exponent to the probability.

Taking into account all the assumptions we made so far the final decision rule will look 
like:

    classify( f_1, ..., f_n ) = argmax_c Prod(1 < i < n) (f_i,c / f_i,total)^f_i

If **f_i,c = 0** or **f_i,total = 0** (respectively a feature never occurred in category 
**c** and a feature never occurred in any category), the above formula degenerates.
To avoid such problem, the formula is improved by adding an additive Laplace smoothing:

    classify( f_1, ..., f_n ) = argmax_c Prod(1 < i < n) ( (f_i,c + alpha) / (f_i,total + k * alpha) ) ^ f_i

Since we are going to add such smoothing for each possible category, we set **alpha = 1** and **k = |c|**.

In order to avoid the arithmetic underflow that may happen when multiplying many numbers in 
the range $$0 < x < 1$$, we apply the logarithm to the formula:
  
    classify( f_1, ..., f_n ) = argmax_c Sum(1 < i < n) ( f_i * ln (f_i,c + alpha) / (f_i,total + k * alpha) )

## Terminology
__Sample__

__Feature__

__Category__



## Implementation
### Dataset
The `Dataset` class is an object that allow to organise a set of _samples_ by _category_. 
This class comes handy when building the _training set_ to train a Naive Bayes classifier and the _control set_ to test it. 
The class has been made _generic_ so that it can be specialised to support any kind of user data.

For instance, if the user uses a `Path` to refer to a file and wants to organise them by `Language`, she may want to write:

    Dataset<Path, Language> trainingSet = new Dataset<>();
    
The _samples_ can be added or removed to the `Dataset` by using one of the provided methods:

    trainingSet.add(Language.ENG, Paths.get("The Canterbury's Tales.txt");
    trainingSet.add(Language.ITA, Arrays.asList<>(
        Paths.get("La Divina Commedia.txt"),
        Paths.get("I Promessi Sposi.txt")));
    trainingSet.remove(Language.ENG);
    assert trainingSet.size() == 2 : "trainingSet contains 2 Italian documents";

The class also provides other methods to inspect the content of the `Dataset`:

    for (Language language: trainingSet.categories()) {
        System.out.println(trainingSet.size(language) + " documents found");
        Collection<Path> samples = trainingSet.getSamples(language);
        for (Path sample : samples) {
            System.out.println("- " + sample.toString());
        }
    }

Last but not least, the class provide two methods to _marshall/unmarshall_ the `Dataset` into a file:

    Path resource = Paths.get("training.dat");
    Dataset<Path, Language> trainingSet = Dataset.load(resource); 
    if (null == trainingSet) {
        trainingSet = new Dataset<>();
        trainingSet.add(Language.ENG, Paths.get("The Canterbury's Tales.txt");
        trainingSet.save(resource);
    }

### Classifier
The `Classifier` class actually implements the Naive Bayes classifier. 
As for the `Dataset`, it has been made _generic_ to better fit the user's needs.
It also includes an inner class `Builder` which is an helper facility to assist the user during the training of the classifier.

For instance, if we plan to use the words as _features_ and the language as _categories_: 

    Classifier.Builder<String, Language> builder = new Builder<>();
    for (Language category : dataset.categories()) {
        Collection<Path> samples = dataset.getSamples(category);
    	for (Path sample : samples) {
    		Map<String, Double> features = read(sample);
    		    builder.add(category, features);
    		}
    	}
    }
    Classifier<String, Language> classifier = builder.build();
    
As for the `Dataset`, `Classifier` comes with two methods to _marshall/unmarshall_ the classifier.
Moreover it is possible to improve the training of a classifier by passing it to a `Builder`:

    Path resource = Paths.get("classifier.dat");
    Classifier<String, Language> classifier = Classifier.load(resource);
    Classifier.Builder<String, Language> builder = (null == classifier) ?
        new Builder<>() :
        new Builder<>(classifier);
    Map<String, Double> features = read(Paths.get("Il Visconte Dimezzato.txt"));        		
    builder.add(Language.ITA, features);
    classifier = builder.build();
    classifier.save(resource);

Notice however that most of the times, you will not need to directly use the `Builder` class since the `Classifier` provides methods for training and testing instances.
These methods cover classic `learning` against a _training set_, `co-learning` against a _training set_ and a collection of _unclassified samples_ (on it's way!) and `testing` against a _control set_, of course! 
Since there are several variants of _classifiers_, these methods use    

`Multonomial`, `Binomial` and `Bernoulli` (to come).