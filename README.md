# NaiveBayes
Java Framework for Naive Bayes classifiers


##Some Theory
**Naive Bayesian classifiers** are very popular because their models are easy to build (requiring no complicated iterative parameter estimation) which makes them particularly suitable for large datasets.
Despite their simplicity, the *Naive Bayesian classifiers* are often surprisingly good and they usually outperforms other more sophisticated classification methods.

As their name suggests, the *Naive Bayesian classifier* is based on the **Bayes' theorem** *with independence assumption between predictors* (see later).

The formula of this theorem is the following:

              P(x|c) • P(c)
    P(c|x) = ---------------
                  P(x)

where
- **P(c)** is the *prior probability* (or *marginal probability*) of the *class* **c**
- **P(x)** is the *prior probability* (or *marginal probability*) of the *predictor* (or *attribute*) **x**
- **P(x|c)** is the *likelihood* (or *conditional probability*) of the *predictor* (or *attribute*) **x** given *class* **c**
and
- **P(c|x)** is the *posterior probability* (or *conditional probability*) of the *class* **c** given *predictor* (or *attribute*) **x** 
which provide a way of calculating the posterior probability `P(c|x)` from `P(c)`, `P(x)`, and `P(x|c)`.

This formula derives from the simpler formula for **conditional probability**:

              P(a∩b)
    P(a|b) = --------
               P(b)

Similarly, however

              P(b∩a)     P(a∩b)
    P(b|a) = -------- = --------
               P(a)       P(a)

By equating the term `P(a∩b)` in both formulas, one may obtain:

    P(a|b) • P(b) = P(b|a) • P(a)  

which leads to the *Bayes' theorem* formula.

####Example
In order to make clear how the *Bayes' theorem* works, consider the following example.

*In a school, the 60% of the students is male and the remaining 40% female.*
*Half of the female students wear trousers (50%) and the other half skirts (50%), while all the male students wear trousers (100%).*
*Suppose you see a student wearing trousers from away: what is the probability that she (he) is female (male)?*

What we what to discover is the **posterior probability** that the student is female/male given the fact that wears trousers (`P(female|trousers)` and `P(male|trousers)`).
We know that
- **P(female)** `= 40% = 2/5`
- **P(male)** `= 60% = 3/5`
- **P(trouser|female)** `= 50% = 1/2`
- **P(trouser|male)** `= 100% = 1`
- **P(trousers)** `= 50% • 40% + 100% • 60% = 20% + 60% = 80% = 4/5`
therefore we can derive the following:

                            1/2 • 2/5                                 1 • 3/5
     P(female|trousers) = ----------- = 1/4 ,    P(male|trousers) = --------- = 3/4  
                              4/5                                      4/5


###Assumption of Independence between Predictors
*With reference to the above example, suppose that the school is attended either by middle-school and high-school students.*
*The middle-school students are the 75% of all the students and wear blue uniforms. High-school students (25%) wear red uniforms instead.*
*What is the probability that a student is female (male) if we see from away her (him) wearing blue trousers?* 

In this case, we are looking for the **posterior probability** that the student is female/male given the fact that wears trousers and that the uniform is blue (`P(female|trousers|blue)` and `P(male|trousers|blue)`).
Let's consider `P(female|trousers|blue)` first. If we expand the *Bayes' theorem*, we obtain the following:

                               P(blue|female|trousers) • P(blue)
    P(female|trousers|blue) = -----------------------------------
                                      P(female|trousers)

We know that **P(blue)** `= 70% = 3/4` and **P(female|trousers)** `= 1/4` from above but unfortunately the problems does not tell us how many middle-school students (blue uniforms) are female and wearing trousers (`P(blue|female|trousers)`).
Without that value, we can not apply the *Bayes' theorem* and we are stuck. But how about we consider wearing blue/red uniforms and trousers/skirts two completely uncorrelated facts?

Such assumption, which goes by the name **independence assumption between predictors**, allows to solve the problem with the data we have **but the answer will only be an esteem and not actually the sought probability**.
In same cases like this example in which the exact probability is needed, this assumption does not make sense but in other cases like **classification tasks** it does.
If you want to **guess the category to which a given sample pertains given some features whose probability is known**, instead, it's safe and sound since the proportion between the likelihood of the possible categories are kept despite the exact values are missing.  

In case we have *several predictors*, the **Bayes' theorem under the independence assumption between predictors** specialises as follows:
 
 
 

Naive Bayes classifier assume that the effect of the value of a predictor (x) on a given class (c) is independent of the values of other predictors. This assumption is called class conditional independence.







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

### Processor
The `Processor` class provides a unified and generic way to extract the set of _features_ out of a _sample_.
The task performed by a `Processor<Sample, Feature>` consists in 
- an optional pre-processing phase (including any number of rewriting steps embodied by instances of the `Refiner<Sample>` class) which attempts to refine the _sample_,
- a compulsory phase in which all the _features_ of the _sample_ are extracted from its content by means of an instance of `Finder<Sample, Feature>`,
- another optional post-processing phase (including again any number of steps embodied by instances of the `Refiner<Feature>` class) which attempts to refine each _feature_ singularly, 
- a mandatory final phase during which each _feature_ is appropriately accounted thanks to a `Variant<Feature>`'s instance. 

    public class Cleaner implements Refiner<String> {
        @Override
        public String process(String sample) {
            Objects.requireNotNull(sample);
            
            sample = sample.replaceAll("\"<>#-_!?;:,\\.\\(\\)\\[\\]\\{\\}", " ");
            String temp;
            do {
                temp = sample;
                sample = temp.replaceAll("  ", " ");
            } while (!temp.equals(sample));
            return sample.trim();
        }
    }
    
    public class Splitter implements Finder<Sample, Feature> {
        @Override
        public Collection<String> process(String sample) {
            Objects.requireNotNull(sample);
            
            String[] samples = sample.split(" ");
            return Arrays.asList(samples);
        }
    }
    
    public class Stemmer implements Refiner<String> {
        private static final PorterStemmer STEMMER = new PorterStemmer();
        @Override
        public String process(String sample) {
            Objects.requireNotNull(sample);
            
            return STEMMER.stem(sample);
        }
    }

    public class Multinomial<Feature> implements Variant<Feature> {
    	@Override
    	public Map<Feature, Double> digest(Collection<Feature> features) {
    		Objects.requireNonNull(features);
    
    		Map<Feature, Double> result = new HashMap<>();
    		for (Feature feature : features) {
    			Objects.requireNonNull(feature);
    			result.put(feature, 1.0 + result.getOrDefault(feature, 0.0));
    		}
    		return result;
    	}
    }

    Processor<String, String> processor = 
        new Processor.Builder<>(new Splitter(), new Multinomial())
            .addSampleRefiner(new Cleaner())
            .addFeatureRefiner(new Stemmer())
            .build();
    

### Classifier
The `Classifier` class actually implements the Naive Bayes classifier. 
As for the `Dataset`, it has been made _generic_ to better fit the user's needs.
It also includes an inner class `Builder` which is an helper facility to assist the user during the training of the classifier.

For instance, if we plan to use the words as _features_ and the language as _categories_: 

    Classifier.Builder<String, Language> builder = new Builder<>();
    for (Language category : dataset.categories()) {
        Collection<Path> samples = dataset.getSamples(category);
    	for (Path sample : samples) {
    		Map<String, Double> features = processor.process(sample);
    		    builder.add(category, features);
    		}
    	}
    }
    Classifier<String, Language> classifier = builder.build();
    
As for the `Dataset`, `Classifier` comes with two methods to _marshall/unmarshall_ the classifier.
Moreover it is possible to reprise the training of a classifier by passing it to a `Builder`:

    Path resource = Paths.get("classifier.dat");
    Classifier<String, Language> classifier = Classifier.load(resource);
    Classifier.Builder<String, Language> builder = (null == classifier) ?
        new Builder<>() :
        new Builder<>(classifier);
    Map<String, Double> features = read(Paths.get("Il Visconte Dimezzato.txt"));        		
    builder.add(Language.ITA, features);
    classifier = builder.build();
    classifier.save(resource);

Most of the times, however, you will not need to directly use the `Builder` class since the `Classifier` provides methods for training and testing instances.
These methods cover classic `learning` against a _training set_, `co-learning` against a _training set_ and a collection of _unclassified samples_ (on it's way!) and `testing` against a _control set_, of course! 
Since there are several variants of _classifiers_, these methods use    

`Multonomial`, `Binomial` and `Bernoulli` (to come).

### Training and testing
The simplest method to train a model consists in 
 prepare a training set, a training set is a dataset whose samples have been 
 Simple training
 
### Running the example
This component comes with an example application.


