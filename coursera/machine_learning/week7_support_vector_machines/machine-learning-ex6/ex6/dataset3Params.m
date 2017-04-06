function [C, sigma] = dataset3Params(X, y, Xval, yval)
%DATASET3PARAMS returns your choice of C and sigma for Part 3 of the exercise
%where you select the optimal (C, sigma) learning parameters to use for SVM
%with RBF kernel
%   [C, sigma] = DATASET3PARAMS(X, y, Xval, yval) returns your choice of C and 
%   sigma. You should complete this function to return the optimal C and 
%   sigma based on a cross-validation set.
%

% You need to return the following variables correctly.
C = 1;
sigma = 0.1;

% ====================== YOUR CODE HERE ======================
% Instructions: Fill in this function to return the optimal C and sigma
%               learning parameters found using the cross validation set.
%               You can use svmPredict to predict the labels on the cross
%               validation set. For example, 
%                   predictions = svmPredict(model, Xval);
%               will return the predictions on the cross validation set.
%
%  Note: You can compute the prediction error using 
%        mean(double(predictions ~= yval))
%

%{
cPool = [0.01; 0.03; 0.1; 0.3; 1; 3; 10; 30];
sigmaPool = [0.01; 0.03; 0.1; 0.3; 1; 3; 10; 30];

valMatrix = zeros(length(cPool) * length(sigmaPool), 3);

index = 1;
for i = 1:length(cPool)
  for j = 1: length(sigmaPool)
    valMatrix(index, 1) = cPool(i);
    valMatrix(index, 2) = sigmaPool(j);
    index = index + 1;
  end
end

for i = 1: size(valMatrix)(1)
  C = valMatrix(i, 1);
  sigma = valMatrix(i, 2);
  fprintf('Evaluating test %d : C = %.2f, sigma = %.2f\n', i, C, sigma);
  model = svmTrain(X, y, C, @(x1, x2) gaussianKernel(x1, x2, sigma));
  predictions = svmPredict(model, Xval);
  error = mean(double(predictions ~= yval));
  fprintf('The error for the example: %.4f\n', error);
  valMatrix(i, 3) = error;
  fprintf('---------------------------------------\n');
end

[error, index] = min(valMatrix(:, 3));
C = valMatrix(index, 1);
sigma = valMatrix(index, 2);
fprintf('\nBest parameters: Error = %.4f, C = %.2f, sigma = %.2f\n', error, C, sigma);
%}
% =========================================================================

end
